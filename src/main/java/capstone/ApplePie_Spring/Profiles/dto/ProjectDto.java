package capstone.ApplePie_Spring.Profiles.dto;

import capstone.ApplePie_Spring.Profiles.domain.Project;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectDto {
    private String introduce;
    private String part;
    private String projectSelf;
    private boolean open;

    @Builder
    public ProjectDto(String introduce, String part, String projectSelf, boolean open) {
        this.part = part;
        this.projectSelf = projectSelf;
        this.open = open;
        this.introduce = introduce;
    }
}
