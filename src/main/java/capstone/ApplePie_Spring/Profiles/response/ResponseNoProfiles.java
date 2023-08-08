package capstone.ApplePie_Spring.Profiles.response;

import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;

@Getter
public class ResponseNoProfiles extends ResponseType {
    public ResponseNoProfiles(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
