package capstone.ApplePie_Spring.Team.dto;

import capstone.ApplePie_Spring.Team.domain.Team;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FindTeamDto {
    private String teamName;
    private String teamContent;
    private List<Integer> memberCount;
    private List<Integer> totalCount;

    @Builder
    public FindTeamDto(Team team) {
        this.teamName = team.getTeamName();
        this.teamContent = team.getTeamContent();
        this.memberCount = team.getCount();
        this.totalCount = team.getTotalCount();
    }
}
