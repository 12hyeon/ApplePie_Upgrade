package capstone.ApplePie_Spring.Team.dto;

import capstone.ApplePie_Spring.Board.domain.Board;
import capstone.ApplePie_Spring.Team.domain.Volunteer;
import lombok.Getter;

@Getter
public class TeamVolunteerDto {
    private Long id;

    private Long userId;
    private String title;
    private String content;

    private Board.Category category;

    private Volunteer.Role role;
}
