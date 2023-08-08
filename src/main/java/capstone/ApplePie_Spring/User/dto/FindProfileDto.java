package capstone.ApplePie_Spring.User.dto;

import capstone.ApplePie_Spring.User.domain.Profile;
import capstone.ApplePie_Spring.User.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FindProfileDto {

    private String email;
    private String name;
    private String nickname;
    private boolean corp;
    private char gender;
    private LocalDate birth;
    private String area;
    private String college;
    private String grader;
    private float totalGrade;
    private float grade;
    private String github;
    private String devFramework;
    private List<String> devLanguage;

    @Builder
    public FindProfileDto(User user, Profile profile, List<String> languages) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.corp = user.isCorp();
        this.gender = user.getGender();
        this.birth = user.getBirth();
        this.area = profile.getArea();
        this.college = profile.getCollege();
        this.grader = profile.getGrader();
        this.grade = profile.getGrade();
        this.totalGrade = profile.getTotalGrade();
        this.github = profile.getGithub();
        this.devFramework = profile.getDevFramework();
        this.devLanguage = languages;
    }
}
