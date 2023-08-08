package capstone.ApplePie_Spring.Board.resposne;

import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;

public class ResponseNoBoard extends ResponseType {
    public ResponseNoBoard(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}
