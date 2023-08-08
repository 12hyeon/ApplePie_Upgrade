package capstone.ApplePie_Spring.Profiles.domain;

import capstone.ApplePie_Spring.Profiles.dto.ProjectDto;
import capstone.ApplePie_Spring.User.domain.Profile;
import capstone.ApplePie_Spring.config.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "PROJECT")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String part;

    @Column(name = "project_self")
    private String projectSelf;

    @Column(nullable = false)
    private boolean open;

    @Column(length = 15)
    private String introduce;

    // 연관 관계 매핑
    @JsonIgnore
    @OneToOne
    private Profile profile;

    // 연관관계 메소드
    public void delete() {
        super.delete();
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Builder
    public Project(String introduce, String part, String projectSelf, boolean open, Profile profile) {
        this.introduce = introduce;
        this.part = part;
        this.projectSelf = projectSelf;
        this.open = open;
        this.profile = profile;
    }

    public void update(Project project) {
        this.part = project.getPart();
        this.projectSelf = project.getProjectSelf();
        this.open = project.isOpen();
    }
}