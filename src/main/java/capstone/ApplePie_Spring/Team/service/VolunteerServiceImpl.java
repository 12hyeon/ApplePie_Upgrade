package capstone.ApplePie_Spring.Team.service;

import capstone.ApplePie_Spring.Team.domain.Team;
import capstone.ApplePie_Spring.Team.domain.TeamVolunteer;
import capstone.ApplePie_Spring.Team.domain.Volunteer;
import capstone.ApplePie_Spring.Team.dto.CancelVolunteerDto;
import capstone.ApplePie_Spring.Team.dto.FindVolunteerDto;
import capstone.ApplePie_Spring.Team.dto.VolunteerDto;
import capstone.ApplePie_Spring.Team.repository.TeamRepository;
import capstone.ApplePie_Spring.Team.repository.TeamVolunteerRepository;
import capstone.ApplePie_Spring.Team.repository.VolunteerRepository;
import capstone.ApplePie_Spring.Team.resposne.ResponseTeam;
import capstone.ApplePie_Spring.Team.resposne.ResponseVolunteerList;
import capstone.ApplePie_Spring.User.domain.User;
import capstone.ApplePie_Spring.User.repository.UserRepository;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {

    private static final Integer STATUS = 1;

    private final VolunteerRepository volunteerRepository;
    private final TeamVolunteerRepository teamVolunteerRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Override
    public Object save(VolunteerDto volunteerDto) {
        Optional<User> findUser = userRepository.findByIdAndStatus(volunteerDto.getUserId(), STATUS);
        Optional<Team> findTeam = teamRepository.findByBoardIdAndStatus(volunteerDto.getBoardId(), STATUS);
        if (findTeam.isEmpty()) {
            return new ResponseTeam(ExceptionCode.TEAM_FIND_NOT);
        }
        else if (findUser.isEmpty()) {
            return new ResponseTeam(ExceptionCode.USER_FIND_NOT);
        }


        Optional<Volunteer> findVolunteer = volunteerRepository.findByUserIdAndTeamIdAndStatus
                (findUser.get().getId(), findTeam.get().getId(), STATUS);
        if (findVolunteer.isPresent()) {
            return new ResponseTeam(ExceptionCode.VOLUNTEER_CREATED_ERROR);
        }

        User user = findUser.get();
        Team team = findTeam.get();

        Volunteer volunteer = Volunteer.builder()
                .user(findUser.get())
                .team(findTeam.get())
                .application(volunteerDto.getApplication())
                .role(volunteerDto.getRole())
                .volunteerStatus(Volunteer.VolunteerStatus.APPLY)
                .build();
        volunteerRepository.save(volunteer);
        findTeam.get().addVolunteer(volunteer);

        TeamVolunteer teamVolunteer = TeamVolunteer.builder()
                .board(team.getBoard())
                .user(user)
                .role(volunteerDto.getRole())
                .build();
        teamVolunteerRepository.save(teamVolunteer);

        return new ResponseTeam(ExceptionCode.VOLUNTEER_OK);

    }

    @Override
    public Object cancel(CancelVolunteerDto volunteerDto) {
        Optional<Team> findTeam = teamRepository.findByBoardIdAndStatus(volunteerDto.getBoardId(), STATUS);
        Optional<User> findUser = userRepository.findByIdAndStatus(volunteerDto.getUserId(), STATUS);
        if (findUser.isEmpty()) {
            return new ResponseTeam(ExceptionCode.USER_FIND_NOT);
        }
        else if (findTeam.isEmpty()) {
            return new ResponseTeam(ExceptionCode.TEAM_FIND_NOT);
        }

        Optional<Volunteer> findVolunteer = volunteerRepository.findByUserIdAndTeamIdAndStatus(findUser.get().getId(),findTeam.get().getId(), STATUS);
        if (findVolunteer.isEmpty()) {
            return new ResponseTeam(ExceptionCode.VOLUNTEER_FIND_NOT);
        }

        Volunteer volunteer = findVolunteer.get();
        if (volunteer.getVolunteerStatus().equals(Volunteer.VolunteerStatus.APPLY)) {
            volunteer.delete();
            findTeam.get().removeVolunteer(volunteer);
            return new ResponseTeam(ExceptionCode.VOLUNTEER_CANCEL_OK);
        }
        else {
            return new ResponseTeam(ExceptionCode.VOLUNTEER_CANCEL_NOT);
        }
    }

    @Override
    public Object getVolunteers(Long uid, FindVolunteerDto findVolunteerDto) {
        Optional<Team> findTeam = teamRepository.findByIdAndStatus(findVolunteerDto.getTeamId(), STATUS);
        if (findTeam.isEmpty()) {
            return new ResponseTeam(ExceptionCode.TEAM_FIND_NOT);
        }

        Optional<User> findUser = userRepository.findByIdAndStatus(uid, STATUS);
        if (findUser.isEmpty() || ! findTeam.get().getUser().getId().equals(uid)) {
            return new ResponseTeam(ExceptionCode.WRONG_APPROACH);
        }

        List<Volunteer> findVolunteer = volunteerRepository.findAllByTeamIdAndStatus(findVolunteerDto.getTeamId(), STATUS);
        return new ResponseVolunteerList(ExceptionCode.VOLUNTEER_FIND_OK, findVolunteer,
                findTeam.get().getCount(), findTeam.get().getTotalCount());
    }
}
