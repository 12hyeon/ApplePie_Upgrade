package capstone.ApplePie_Spring.Profiles.response;

import capstone.ApplePie_Spring.Board.domain.Board;
import capstone.ApplePie_Spring.Profiles.domain.Lesson;
import capstone.ApplePie_Spring.Profiles.domain.Outsourcing;
import capstone.ApplePie_Spring.Profiles.domain.Project;
import capstone.ApplePie_Spring.Profiles.dto.ProfilesListDto;
import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseLessonList extends ResponseType {
    private List<ProfilesListDto> data;

    public ResponseLessonList(ExceptionCode exceptionCode, List<Lesson> lessons) {
        super(exceptionCode);
        this.data = new ArrayList<>();
        for (Lesson lesson : lessons) {
            data.add(ProfilesListDto.builder()
                    .id(lesson.getId())
                    .introduce(lesson.getIntroduce())
                    .category(Board.Category.LESSON)
                    .build());
        }
    }

}
