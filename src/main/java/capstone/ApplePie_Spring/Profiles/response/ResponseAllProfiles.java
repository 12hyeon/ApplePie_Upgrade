package capstone.ApplePie_Spring.Profiles.response;

import capstone.ApplePie_Spring.Profiles.domain.Lesson;
import capstone.ApplePie_Spring.Profiles.domain.Outsourcing;
import capstone.ApplePie_Spring.Profiles.domain.Project;
import capstone.ApplePie_Spring.Profiles.dto.LessonDto;
import capstone.ApplePie_Spring.Profiles.dto.OutsourcingDto;
import capstone.ApplePie_Spring.Profiles.dto.ProjectDto;
import capstone.ApplePie_Spring.User.domain.Profile;
import capstone.ApplePie_Spring.User.domain.User;
import capstone.ApplePie_Spring.User.dto.FindProfileDto;
import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static capstone.ApplePie_Spring.Profiles.response.ResponseOneProfiles.*;

@Getter
@Setter
public class ResponseAllProfiles extends ResponseType {
    private Long profileId;
    private FindProfileDto data;

    private LessonDto lesson;
    private ProjectDto project;
    private OutsourcingDto outsourcing;

    public ResponseAllProfiles
            (ExceptionCode exceptionCode, User user, Profile profile,
             Lesson lesson, Project project, Outsourcing outsourcing)
    {
        super(exceptionCode);
        this.lesson = toLessonDto(lesson);
        this.project = toProjectDto(project);
        this.outsourcing = toOutsourcingDto(outsourcing);


        this.profileId = profile.getId();
        List<String> languages = new ArrayList<>();
        if (profile.getDevLanguage() != null) {
            for (Profile.Language language : profile.getDevLanguage()) {
                languages.add(language.getLanguageName());
            }
        }
        this.data = FindProfileDto.builder()
                .user(user)
                .profile(profile)
                .languages(languages)
                .build();
    }
}
