package capstone.ApplePie_Spring.Team.dto;

import capstone.ApplePie_Spring.Team.domain.Volunteer;
import lombok.Getter;

import java.util.List;

@Getter
public class TeamDto {
    private Long boardId;
    private String teamName;
    private String teamContent;
    private List<Integer> count;
    private int memberCount;
    private Volunteer.Role role; // 팀장 역할

    public void addLeaderCount() {
        int index = role.ordinal();
        this.count.set(index, this.count.get(index)+1);
    }
}
