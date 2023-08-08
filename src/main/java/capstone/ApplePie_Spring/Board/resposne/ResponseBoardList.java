package capstone.ApplePie_Spring.Board.resposne;

import capstone.ApplePie_Spring.Board.dto.FindBoardListDto;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseBoardList extends ResponseNoBoard {
    private List<FindBoardListDto> boardList;

    public ResponseBoardList(ExceptionCode exceptionCode, List<FindBoardListDto> boardList) {
        super(exceptionCode);
        this.boardList = boardList;
    }
}
