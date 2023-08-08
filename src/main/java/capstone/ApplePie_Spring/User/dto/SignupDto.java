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
public class SignupDto {
    private String email;
    private String name;
    private String nickname;
    private String password;
    private boolean corp;

    private char gender;
    private int age;
    private LocalDate birth;

    private String area;
    private String college;
    private String grader;
    private float totalGrade;
    private float grade;
    private String github;
    private String devFramework;
    private List<Profile.Language> devLanguage;

    @Builder
    public SignupDto(String email, String name, String nickname, String password,
                     boolean corp, char gender, LocalDate birth, float totalGrade, //int age,
                     String college, float grade, String area, String grader, String github,
                     String devFramework, List<Profile.Language> devLanguage) {

        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.password = password;
        this.gender = gender;
        this.age = LocalDate.now().getDayOfYear() - birth.getDayOfYear() + 1;
        this.birth = birth;
        this.corp = corp;

        this.area = area;
        this.college = college;
        this.grade = grade;
        this.totalGrade = totalGrade;
        this.grader = grader;
        this.github = github;
        this.devFramework = devFramework;
        this.devLanguage = devLanguage;
    }

    public User toUser() {
        return User.builder()
                .signupDto(this)
                .build();
    }

    public Profile toProfile(User user) {
        return Profile.builder()
                .signupDto(this)
                .user(user)
                .build();
    }
}
