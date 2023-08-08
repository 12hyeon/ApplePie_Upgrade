package capstone.ApplePie_Spring.User.response;

import capstone.ApplePie_Spring.User.domain.Profile;
import capstone.ApplePie_Spring.User.domain.User;
import capstone.ApplePie_Spring.User.dto.FindProfileDto;
import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseProfile extends ResponseType {

    private Long profileId;
    private FindProfileDto data;

    public ResponseProfile(ExceptionCode exceptionCode, User user, Profile profile) {
        super(exceptionCode);
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

