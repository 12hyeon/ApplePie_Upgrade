package capstone.ApplePie_Spring.Team.dto;

import capstone.ApplePie_Spring.Team.domain.Volunteer;
import lombok.Getter;
import javax.annotation.Nullable;

@Getter
public class VolunteerDto {
    private Long boardId;
    private Long userId;
    private Volunteer.Role role;

    @Nullable
    private String application;
}
