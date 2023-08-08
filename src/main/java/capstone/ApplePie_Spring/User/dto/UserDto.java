package capstone.ApplePie_Spring.User.dto;

import capstone.ApplePie_Spring.User.domain.Profile;
import capstone.ApplePie_Spring.User.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {

    private String email;
    private String name;
    private String nickname;
    private boolean corp;
    private char gender;
    //private int age;

    private LocalDate birth;
    private String area;
    private String college;
    private String grader;
    private float grade;
    private float totalGrade;
    private String github;
    private String devFramework;
    private List<Profile.Language> devLanguage;

    @Builder
    public UserDto(User user, Profile profile) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.nickname = user.getNickname();
        this.corp = user.isCorp();
        this.gender = user.getGender();
        //this.age = user.getAge();
        this.birth = user.getBirth();
        this.totalGrade = profile.getTotalGrade();
        this.area = profile.getArea();
        this.college = profile.getCollege();
        this.grader = profile.getGrader();
        this.grade = profile.getGrade();
        this.github = profile.getGithub();
        this.devFramework = profile.getDevFramework();
        this.devLanguage = profile.getDevLanguage();
    }
}
