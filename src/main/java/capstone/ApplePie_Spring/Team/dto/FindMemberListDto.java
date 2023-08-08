package capstone.ApplePie_Spring.Team.dto;

import capstone.ApplePie_Spring.Team.domain.Volunteer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindMemberListDto {
    private String nickname;
    private Volunteer.Role role;


    public FindMemberListDto(String nickname, Volunteer.Role role) {
        this.nickname = nickname;
        this.role = role;
    }
}
