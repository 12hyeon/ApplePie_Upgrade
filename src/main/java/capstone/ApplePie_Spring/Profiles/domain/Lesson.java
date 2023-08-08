package capstone.ApplePie_Spring.Profiles.domain;

import capstone.ApplePie_Spring.Profiles.dto.LessonDto;
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
@Entity(name = "LESSON")
public class Lesson extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String subject;

    @Column(name = "lesson_self")
    private String lessonSelf;


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
    public Lesson(String introduce, String subject, String lessonSelf, boolean open, Profile profile) {
        this.introduce = introduce;
        this.subject = subject;
        this.lessonSelf = lessonSelf;
        this.open = open;
        this.profile = profile;
    }

    public void update(Lesson lesson) {
        this.subject = lesson.getSubject();
        this.lessonSelf = lesson.getLessonSelf();
        this.open = lesson.isOpen();
    }

}