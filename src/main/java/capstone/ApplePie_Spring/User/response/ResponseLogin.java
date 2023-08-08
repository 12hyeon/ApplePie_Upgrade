package capstone.ApplePie_Spring.User.response;

import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;

@Getter
public class ResponseLogin extends ResponseUser {
    private Long uid;
    private Long pid;
    private boolean corp;

    public ResponseLogin(ExceptionCode exceptionCode, Long uid, Long pid, boolean corp) {
        super(exceptionCode);
        this.uid = uid;
        this.pid = pid;
        this.corp = corp;
    }
}
