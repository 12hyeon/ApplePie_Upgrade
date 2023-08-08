package capstone.ApplePie_Spring.Profiles.dto;

import capstone.ApplePie_Spring.Profiles.domain.Lesson;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LessonDto {
    private String introduce;
    private String subject;
    private String lessonSelf;
    private boolean open;

    @Builder
    public LessonDto(String subject, String lessonSelf, String introduce, boolean open) {
        this.subject = subject;
        this.lessonSelf = lessonSelf;
        this.open = open;
        this.introduce = introduce;
    }
}
