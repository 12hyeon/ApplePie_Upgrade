package capstone.ApplePie_Spring.Team.service;

import capstone.ApplePie_Spring.Board.domain.Board;
import capstone.ApplePie_Spring.Board.dto.FindBoardListDto;
import capstone.ApplePie_Spring.Board.repository.BoardRepository;
import capstone.ApplePie_Spring.Board.resposne.ResponseNoBoard;
import capstone.ApplePie_Spring.Board.service.FileService;
import capstone.ApplePie_Spring.Profiles.domain.Lesson;
import capstone.ApplePie_Spring.Profiles.domain.Outsourcing;
import capstone.ApplePie_Spring.Profiles.domain.Project;
import capstone.ApplePie_Spring.Profiles.repository.LessonRepository;
import capstone.ApplePie_Spring.Profiles.repository.OutsourcingRepository;
import capstone.ApplePie_Spring.Profiles.repository.ProjectRepository;
import capstone.ApplePie_Spring.Profiles.response.ResponseNoProfiles;
import capstone.ApplePie_Spring.Team.domain.Member;
import capstone.ApplePie_Spring.Team.domain.TeamVolunteer;
import capstone.ApplePie_Spring.Team.domain.Volunteer;
import capstone.ApplePie_Spring.Team.domain.Team;
import capstone.ApplePie_Spring.Team.dto.TeamDto;
import capstone.ApplePie_Spring.Team.dto.TeamUpdateDto;
import capstone.ApplePie_Spring.Team.repository.MemberRepository;
import capstone.ApplePie_Spring.Team.repository.TeamVolunteerRepository;
import capstone.ApplePie_Spring.Team.repository.VolunteerRepository;
import capstone.ApplePie_Spring.Team.repository.TeamRepository;
import capstone.ApplePie_Spring.Team.resposne.ResponseTeam;
import capstone.ApplePie_Spring.Team.resposne.ResponseUserTeam;
import capstone.ApplePie_Spring.User.domain.Profile;
import capstone.ApplePie_Spring.User.domain.User;
import capstone.ApplePie_Spring.User.repository.ProfileRepository;
import capstone.ApplePie_Spring.User.repository.UserRepository;
import capstone.ApplePie_Spring.User.response.ResponseUser;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private static final Integer STATUS = 1;
    // private static final Integer COMPLETE = 2; TEAM에서는 TEAM_STATUS로 구분

    private final LessonRepository lessonRepository;
    private final ProfileRepository profileRepository;
    private final OutsourcingRepository outsourcingRepository;
    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final FileService fileService;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final VolunteerRepository volunteerRepository;
    private final TeamVolunteerRepository teamVolunteerRepository;

    @Override
    public Object save(Long uid, TeamDto teamDto) {
        Optional<Board> findBoard = boardRepository.findByIdAndStatus(teamDto.getBoardId(), STATUS);
        Optional<Team> findTeam = teamRepository.findByBoardIdAndStatus(teamDto.getBoardId(), STATUS);
        if (findBoard.isEmpty()) {
            return new ResponseNoBoard(ExceptionCode.BOARD_FIND_NOT);
        }
        else if (findTeam.isPresent()) {
            return new ResponseTeam(ExceptionCode.TEAM_CREATED_ERROR);
        }

        Optional<User> findUser = userRepository.findByIdAndStatus(uid, STATUS);
        if (findUser.isEmpty() || ! findUser.get().getId().equals(uid)) {
            return new ResponseTeam(ExceptionCode.WRONG_APPROACH);
        }

        Board board = findBoard.get();
        String email = findBoard.get().getUser().getEmail();
        User user = userRepository.findByEmailAndStatus(email, STATUS).get();

        // team
        teamDto.addLeaderCount();  // 팀장 역할 반영
        Team team = Team.builder()
                .board(board)
                .teamDto(teamDto)
                .user(user)
                .build();
        teamRepository.save(team);
        board.setTeam(team);

        Volunteer volunteer = Volunteer.builder()
                .team(team)
                .user(user)
                .application(teamDto.getRole().name()) // 내용은 임시로 역할명
                .role(teamDto.getRole())
                .volunteerStatus(Volunteer.VolunteerStatus.APPLY)
                .build();
        volunteerRepository.save(volunteer);
        team.addVolunteer(volunteer);

        // user
        Member member = Member.builder()
                .team(team)
                .volunteer(volunteer)
                .build();
        memberRepository.save(member);
        volunteer.setVolunteerStatus(Volunteer.VolunteerStatus.COMPLETE);
        team.addMember(member);

        // ai 칼럼
        TeamVolunteer teamVolunteer = TeamVolunteer.builder()
                .board(team.getBoard())
                .user(user)
                .role(teamDto.getRole())
                .build();
        teamVolunteerRepository.save(teamVolunteer);

        return new ResponseTeam(ExceptionCode.TEAM_CREATED_OK, team);
    }

    @Override
    public Object complete(TeamUpdateDto teamUpdateDto) {
        // 모든 인원 초과
        Optional<Team> findTeam = teamRepository.findByIdAndStatus(teamUpdateDto.getTeamId(), STATUS);
        if (findTeam.isEmpty()) {
            return new ResponseTeam(ExceptionCode.TEAM_FIND_NOT);
        }

        Team team = findTeam.get();
        team.setTeamStatus(Team.TeamStatus.COMPLETE);
        return new ResponseTeam(ExceptionCode.TEAM_COMPLETE);
    }

    @Override
    public Object update(Long uid, TeamDto teamDto) {
        Optional<Board> findBoard = boardRepository.findByIdAndStatus(teamDto.getBoardId(), STATUS);
        Optional<Team> findTeam = teamRepository.findByBoardIdAndStatus(teamDto.getBoardId(), STATUS);
        if (findBoard.isEmpty()) {
            return new ResponseNoBoard(ExceptionCode.BOARD_FIND_NOT);
        }
        else if (findTeam.isEmpty()) {
            return new ResponseTeam(ExceptionCode.TEAM_FIND_NOT);
        }

        Optional<User> findUser = userRepository.findByIdAndStatus(uid, STATUS);
        if (findUser.isEmpty() || ! findUser.get().getId().equals(uid)) {
            return new ResponseTeam(ExceptionCode.WRONG_APPROACH);
        }

        Team team = findTeam.get();

        // totalCount 설정
        teamDto.addLeaderCount(); // 팀장 역할 반영
        team.setTotalCount(teamDto.getCount());

        // count 재설정
        List<Member> findMember = memberRepository.findAllByTeamIdAndStatus(team.getId(), STATUS);
        team.setCount(findMember);

        // 모든 인원수가 이상인 경우에 complete 처리
        List<Integer> total = findTeam.get().getTotalCount();
        List<Integer> cnt = findTeam.get().getCount();
        for (int n=0; n < 5; n++) {
            if (total.get(n) <= cnt.get(n)) {
                team.setTeamStatus(Team.TeamStatus.COMPLETE);
                return new ResponseTeam(ExceptionCode.TEAM_UPDATE_OK);
            }
        }
        return new ResponseTeam(ExceptionCode.TEAM_UPDATE_OK);
    }

    @Override
    public Object delete(Long uid, TeamUpdateDto teamUpdateDto) {
        Optional<User> findUser = userRepository.findByIdAndStatus(uid, STATUS);
        if (findUser.isEmpty() || ! findUser.get().getId().equals(uid)) {
            return new ResponseTeam(ExceptionCode.WRONG_APPROACH);
        }

        Optional<Team> findTeam = teamRepository.findByIdAndStatus(teamUpdateDto.getTeamId(), STATUS);
        if (findTeam.isEmpty()) {
            return new ResponseTeam(ExceptionCode.TEAM_FIND_NOT);
        }

        findTeam.get().delete();
        return new ResponseTeam(ExceptionCode.TEAM_COMPLETE);
    }

    @Override
    public Object findTeam(Long userId) {
        Optional<User> findUser = userRepository.findByIdAndStatus(userId, STATUS);
        if (findUser.isEmpty()) {
            return new ResponseTeam(ExceptionCode.USER_FIND_NOT);
        }

        Optional<Profile> findProfile = profileRepository.findByUserIdAndStatus(userId, STATUS);
        if (findProfile.isEmpty()) {
            return new ResponseUser(ExceptionCode.USER_PROFILE_FIND_NOT);
        }
        Profile profile = findProfile.get();

        Optional<Lesson> findLesson = lessonRepository.findByProfileIdAndStatus(profile.getId(), STATUS);
        Optional<Project> findProject = projectRepository.findByProfileIdAndStatus(profile.getId(), STATUS);
        Optional<Outsourcing> findOutsourcing = outsourcingRepository.findByProfileIdAndStatus(profile.getId(), STATUS);

        if (findLesson.isEmpty() || findProject.isEmpty() || findOutsourcing.isEmpty()) {
            return new ResponseUser(ExceptionCode.PROFILES_FIND_NOT);
        }

        List<FindBoardListDto> board = myBoardPagesBy(userId);
        List<Team> result1 = findCompleteTeam(userId);
        List<Team> result2 = findUserTeam(userId);
        List<Team> result3 = findApplyTeam(userId);
        return new ResponseUserTeam
                (ExceptionCode.TEAM_FIND_OK, findLesson.get().isOpen(), findProject.get().isOpen(), findOutsourcing.get().isOpen(),
                board, result1, result2, result3);
    }

    public List<FindBoardListDto> myBoardPagesBy(Long uid) {
        List<Board> findBoard = boardRepository.findAllByUserIdAndStatusAndDeadlineGreaterThanEqualOrderByIdDesc(uid, STATUS, LocalDate.now());
        List<FindBoardListDto> findBoardListDtoList = new ArrayList<>();
        for (Board board : findBoard) {
            findBoardListDtoList.add(FindBoardListDto.builder()
                    .board(board)
                    .file(fileService.findOne(board.getId(), 1))
                    .build());
        }
        return findBoardListDtoList;
    }

    @Override  // 지원한 팀 경우 -> 지원 취소 & 자기 팀인 경우 -> 팀 취소(team_status = 0처리)
    public Object cancel(Long uid, TeamUpdateDto teamUpdateDto) { // 모집 취소(중지)
        Optional<Team> findTeam = teamRepository.findByIdAndStatus(teamUpdateDto.getTeamId(), STATUS);
        if (findTeam.isEmpty()) {
            return new ResponseTeam(ExceptionCode.TEAM_FIND_NOT);
        }
        Team team = findTeam.get();

        Optional<User> findUser = userRepository.findByIdAndStatus(uid, STATUS);
        if (findUser.isEmpty() || ! findUser.get().getId().equals(uid)) {
            return new ResponseTeam(ExceptionCode.WRONG_APPROACH);
        }

        // 본인 팀인 경우
        if (team.getUser().getId().equals(uid)) {
            team.setTeamStatus(Team.TeamStatus.CANCEL);
            team.delete();
            return new ResponseTeam(ExceptionCode.TEAM_DELETE_OK);
        }
        return new ResponseTeam(ExceptionCode.TEAM_DELETE_NOT);

        /*// 지원한 팀인 경우
        Volunteer volunteer = volunteerRepository
                .findByUserIdAndTeamIdAndStatus(teamUpdateDto.getUserId(), team.getId(), STATUS).get();
        if (volunteer.getVolunteerStatus().equals(Volunteer.VolunteerStatus.APPLY)) {
            volunteer.delete();
            team.removeVolunteer(volunteer);
            return new ResponseTeam(ExceptionCode.VOLUNTEER_CANCEL_OK);
        }
        else {
            return new ResponseTeam(ExceptionCode.VOLUNTEER_CANCEL_NOT);
        }*/
    }


    public List<Team> findUserTeam(Long userId) { // 내가 모집중인 팀 + 지원한 팀(멤버여도 팀이 완성되지 않은 경우)
        return teamRepository.findAllByUserIdAndTeamStatusAndStatus(userId, Team.TeamStatus.CREATE, STATUS);
    }

    public List<Team> findApplyTeam(Long userId) {
        List<Team> result = new ArrayList<>();

        List<Volunteer> volunteers = findVolunteers(userId);
        for (Volunteer volunteer : volunteers) {
            Optional<Team> ownerTeam = teamRepository.findByIdAndTeamStatusAndStatus(volunteer.getTeam().getId(), Team.TeamStatus.CREATE, STATUS);
            if (ownerTeam.isPresent() && ! ownerTeam.get().getUser().getId().equals(userId)) {
                result.add(ownerTeam.get());
            }
        }
        return result;
    }

    public List<Team> findCompleteTeam(Long userId) {
        List<Team> result = new ArrayList<>();

        List<Volunteer> volunteers = findVolunteers(userId);
        for (Volunteer volunteer : volunteers) {
            Optional<Team> ownerTeam = teamRepository.findByIdAndTeamStatusAndStatus(volunteer.getTeam().getId(), Team.TeamStatus.COMPLETE, STATUS);
            ownerTeam.ifPresent(result::add);
        }

        return result;
    }

    public List<Volunteer> findVolunteers(Long userId) {
        return volunteerRepository.findAllByUserIdAndStatus(userId, STATUS);
    }
}