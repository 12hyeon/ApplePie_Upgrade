package capstone.ApplePie_Spring.Profiles.response;

import capstone.ApplePie_Spring.Profiles.domain.Lesson;
import capstone.ApplePie_Spring.Profiles.domain.Outsourcing;
import capstone.ApplePie_Spring.Profiles.domain.Project;
import capstone.ApplePie_Spring.Profiles.dto.LessonDto;
import capstone.ApplePie_Spring.Profiles.dto.OutsourcingDto;
import capstone.ApplePie_Spring.Profiles.dto.ProjectDto;
import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // null 정보  제거
public class ResponseProfiles extends ResponseType {
    private LessonDto lesson;
    private ProjectDto project;
    private OutsourcingDto outsourcing;


    public ResponseProfiles(ExceptionCode exceptionCode, Lesson lesson) {
        super(exceptionCode);
        this.lesson = ResponseOneProfiles.toLessonDto(lesson);
    }

    public ResponseProfiles(ExceptionCode exceptionCode, Project project) {
        super(exceptionCode);
        this.project = ResponseOneProfiles.toProjectDto(project);
    }

    public ResponseProfiles(ExceptionCode exceptionCode, Outsourcing outsourcing) {
        super(exceptionCode);
        this.outsourcing = ResponseOneProfiles.toOutsourcingDto(outsourcing);
    }


}
