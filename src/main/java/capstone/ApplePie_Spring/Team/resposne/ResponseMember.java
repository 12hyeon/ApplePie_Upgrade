package capstone.ApplePie_Spring.Team.resposne;

import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseMember extends ResponseType {

    private List<Integer> totalCount;
    private List<Integer> count;

    public ResponseMember(ExceptionCode exceptionCode, List<Integer> count, List<Integer> totalCount) {
        super(exceptionCode);
        this.count = count;
        this.totalCount = totalCount;
    }
}
