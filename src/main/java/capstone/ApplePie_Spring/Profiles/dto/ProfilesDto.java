package capstone.ApplePie_Spring.Profiles.dto;

import capstone.ApplePie_Spring.Board.domain.Board;
import capstone.ApplePie_Spring.Profiles.domain.Lesson;
import capstone.ApplePie_Spring.Profiles.domain.Outsourcing;
import capstone.ApplePie_Spring.Profiles.domain.Project;
import capstone.ApplePie_Spring.User.domain.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfilesDto {
    private Board.Category category;
    private String introduce;
    private String info;
    private String infoSelf;
    private boolean open;

    public Lesson toLesson(Profile profile) {
        return Lesson.builder()
                .subject(info)
                .lessonSelf(infoSelf)
                .open(open)
                .introduce(introduce)
                .profile(profile)
                .build();
    }

    public Outsourcing toOutsourcing(Profile profile) {
        return Outsourcing.builder()
                .career(info)
                .outsourcingSelf(infoSelf)
                .open(open)
                .introduce(introduce)
                .profile(profile)
                .build();
    }

    public Project toProject(Profile profile) {
        return Project.builder()
                .part(info)
                .projectSelf(infoSelf)
                .open(open)
                .introduce(introduce)
                .profile(profile)
                .build();
    }
}
