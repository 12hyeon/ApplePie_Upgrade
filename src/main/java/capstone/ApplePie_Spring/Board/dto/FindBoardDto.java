package capstone.ApplePie_Spring.Board.dto;

import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public class FindBoardDto {
    private int categoryId;
    @Nullable
    private Long id; // 생략 가능, 전달된 마지막 board id
    @Nullable
    private String keyword; // 생략 가능
    private int size;
}
