package capstone.ApplePie_Spring.User.response;

import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;

@Getter
public class ResponseUser extends ResponseType {
    public ResponseUser(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
