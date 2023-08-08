package capstone.ApplePie_Spring.AI.Response;

import capstone.ApplePie_Spring.AI.domain.AI;
import lombok.Getter;

@Getter
public class ResponseAI {
    private long board_id; //글 아이디 리턴

    public ResponseAI(AI ai){
        this.board_id= ai.getBoardId();
    }
}
