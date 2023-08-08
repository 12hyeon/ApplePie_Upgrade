package capstone.ApplePie_Spring.Team.service;

import capstone.ApplePie_Spring.Team.domain.Member;
import capstone.ApplePie_Spring.Team.domain.Team;
import capstone.ApplePie_Spring.Team.domain.Volunteer;
import capstone.ApplePie_Spring.Team.dto.FindMemberDto;
import capstone.ApplePie_Spring.Team.dto.MemberDto;
import capstone.ApplePie_Spring.Team.repository.MemberRepository;
import capstone.ApplePie_Spring.Team.repository.TeamRepository;
import capstone.ApplePie_Spring.Team.repository.VolunteerRepository;
import capstone.ApplePie_Spring.Team.resposne.ResponseMember;
import capstone.ApplePie_Spring.Team.resposne.ResponseMembers;
import capstone.ApplePie_Spring.Team.resposne.ResponseTeam;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private static final Integer STATUS = 1;

    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final VolunteerRepository volunteerRepository;

    @Override
    public Object save(MemberDto memberDto) {
        Optional<Volunteer> findVolunteer= volunteerRepository.findByIdAndStatus(memberDto.getVolunteerId(), STATUS);
        if (findVolunteer.isEmpty()) {
            return new ResponseTeam(ExceptionCode.VOLUNTEER_FIND_NOT);
        }

        Volunteer volunteer = findVolunteer.get();
        Optional<Team> findTeam = teamRepository.findByIdAndStatus(volunteer.getTeam().getId(), STATUS);
        if (findTeam.isEmpty()) {
            return new ResponseTeam(ExceptionCode.TEAM_FIND_NOT);
        }

        if (findVolunteer.get().getUser().getId().equals(findTeam.get().getUser().getId())) {
            return new ResponseTeam(ExceptionCode.WRONG_APPROACH);
        }

        Team team = findTeam.get();
        Member member = Member.builder()
                .team(team)
                .volunteer(volunteer)
                .build();
        memberRepository.save(member);
        team.addMember(member);
        volunteer.setVolunteerStatus(Volunteer.VolunteerStatus.COMPLETE);

        // 모든 인원수가 이상인 경우에 complete 처리
        List<Integer> total = team.getTotalCount();
        List<Integer> count = team.getCount();

        //System.out.println("count = " + count);
        //System.out.println("total = " + total);
        int n = 0;
        while (n < team.getCount().size()) {
            if (total.get(n) > count.get(n)) {
                return new ResponseMember(ExceptionCode.MEMBER_OK, team.getCount(), team.getTotalCount());
            }
            n++;
        }
        team.setTeamStatus(Team.TeamStatus.COMPLETE);

        teamRepository.save(team);
        return new ResponseTeam(ExceptionCode.TEAM_COMPLETE);
    }

    @Override
    public Object getMembers(FindMemberDto findMemberDto) {
        Optional<Team> findTeam = teamRepository.findByIdAndStatus(findMemberDto.getTeamId(), STATUS);
        if (findTeam.isEmpty()) {
            return new ResponseTeam(ExceptionCode.TEAM_FIND_NOT);
        }

        Team team = findTeam.get();
        List<Member> findMember = memberRepository.findAllByTeamIdAndStatus(team.getId(), STATUS);
        return new ResponseMembers(ExceptionCode.MEMBER_FIND_OK, findMember);
    }
}
