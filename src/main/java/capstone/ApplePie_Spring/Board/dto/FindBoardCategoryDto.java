package capstone.ApplePie_Spring.Board.dto;

import capstone.ApplePie_Spring.Board.domain.Board;
import lombok.Getter;

@Getter
public class FindBoardCategoryDto {
    private Long id;
    private String title;
    private String content;
    private Board.Category categoryId;
    //Role 관련된 변수 선언해야 함
}
