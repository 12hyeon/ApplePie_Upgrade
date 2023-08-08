package capstone.ApplePie_Spring.User.response;

import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;

@Getter
public class ResponseSignup extends ResponseUser {
    private Long uid;
    private Long pid;

    public ResponseSignup(ExceptionCode exceptionCode, Long uid, Long pid) {
        super(exceptionCode);
        this.uid = uid;
        this.pid = pid;
    }
}
