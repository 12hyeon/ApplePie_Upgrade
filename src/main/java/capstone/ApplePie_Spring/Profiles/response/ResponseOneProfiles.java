package capstone.ApplePie_Spring.Profiles.response;

import capstone.ApplePie_Spring.Profiles.domain.Lesson;
import capstone.ApplePie_Spring.Profiles.domain.Outsourcing;
import capstone.ApplePie_Spring.Profiles.domain.Project;
import capstone.ApplePie_Spring.Profiles.dto.LessonDto;
import capstone.ApplePie_Spring.Profiles.dto.OutsourcingDto;
import capstone.ApplePie_Spring.Profiles.dto.ProjectDto;
import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseOneProfiles extends ResponseType {
    private LessonDto lesson;
    private ProjectDto project;
    private OutsourcingDto outsourcing;

    public ResponseOneProfiles(ExceptionCode exceptionCode,
                               Lesson lesson, Project project, Outsourcing outsourcing) {
        super(exceptionCode);
        this.lesson = toLessonDto(lesson);
        this.project = toProjectDto(project);
        this.outsourcing = toOutsourcingDto(outsourcing);
    }

    public static ProjectDto toProjectDto(Project project) {
        ProjectDto projectDto = null;
        if (project != null) {
            projectDto = ProjectDto.builder()
                    .introduce(project.getIntroduce())
                    .part(project.getPart())
                    .projectSelf(project.getProjectSelf())
                    .open(project.isOpen())
                    .build();
        }
        return projectDto;
    }

    public static LessonDto toLessonDto(Lesson lesson) {
        LessonDto lessonDto = null;
        if (lesson != null) {
            lessonDto = LessonDto.builder()
                    .introduce(lesson.getIntroduce())
                    .subject(lesson.getSubject())
                    .lessonSelf(lesson.getLessonSelf())
                    .open(lesson.isOpen())
                    .build();
        }
        return lessonDto;
    }

    public static OutsourcingDto toOutsourcingDto(Outsourcing outsourcing) {
        OutsourcingDto outsourcingDto = null;
        if (outsourcing != null) {
            outsourcingDto = OutsourcingDto.builder()
                    .introduce(outsourcing.getIntroduce())
                    .career(outsourcing.getCareer())
                    .outsourcingSelf(outsourcing.getOutsourcingSelf())
                    .open(outsourcing.isOpen())
                    .build();
        }
        return outsourcingDto;
    }
}
