package capstone.ApplePie_Spring.Team.resposne;

import capstone.ApplePie_Spring.Team.domain.Member;
import capstone.ApplePie_Spring.Team.domain.Volunteer;
import capstone.ApplePie_Spring.Team.dto.FindMemberListDto;
import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseMembers extends ResponseType {

    private List<FindMemberListDto> member;

    public ResponseMembers(ExceptionCode exceptionCode, List<Member> members) {
        super(exceptionCode);
        member = new ArrayList<>();
        for(Member m: members) {
            Volunteer v = m.getVolunteer();
            member.add(new FindMemberListDto(v.getUser().getNickname(), v.getRole()));
        }
    }
}
