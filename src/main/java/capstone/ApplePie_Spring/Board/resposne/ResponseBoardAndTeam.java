package capstone.ApplePie_Spring.Board.resposne;

import capstone.ApplePie_Spring.Board.dto.FindOneBoardDto;
import capstone.ApplePie_Spring.Team.dto.FindTeamDto;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;

@Getter
public class ResponseBoardAndTeam extends ResponseNoBoard {

    private FindOneBoardDto board;
    private FindTeamDto team;

    public ResponseBoardAndTeam(ExceptionCode exceptionCode, FindOneBoardDto findOneBoardDto, FindTeamDto findTeamDto) {
        super(exceptionCode);
        board = findOneBoardDto;
        team = findTeamDto;
    }
}
