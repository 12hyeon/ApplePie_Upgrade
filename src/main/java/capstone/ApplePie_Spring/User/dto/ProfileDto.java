package capstone.ApplePie_Spring.User.dto;

import capstone.ApplePie_Spring.User.domain.Profile;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDto {
    private Long userId;

    private String area;
    private String college;
    private String grader;
    private float totalGrade;
    private float grade;
    private String github;
    private String devFramework;
    private List<Profile.Language> devLanguage;

    @Builder
    public ProfileDto(String college, float grade, float totalGrade, String area, String grader, String github,
                      String devFramework, List<Profile.Language> devLanguage) {
        this.area = area;
        this.college = college;
        this.grade = grade;
        this.totalGrade = totalGrade;
        this.grader = grader;
        this.github = github;
        this.devFramework = devFramework;
        this.devLanguage = devLanguage;
    }

    public Profile toProfile(ProfileDto profileDto) {
        return Profile.updateBuilder()
                .profileDto(profileDto)
                .build();
    }
}