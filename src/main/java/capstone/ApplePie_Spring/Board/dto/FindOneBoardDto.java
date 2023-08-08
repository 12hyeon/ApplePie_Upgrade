package capstone.ApplePie_Spring.Board.dto;

import capstone.ApplePie_Spring.Board.domain.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class FindOneBoardDto {

    private String nickname;
    private Long id;
    private String title;
    private String content;
    private int viewCount;
    private Board.Category category;

    private LocalDate deadline;
    private List<String> files;

    @Builder
    public FindOneBoardDto(Board board, List<String> urlList) {
        this.id = board.getId();
        this.nickname = board.getUser().getNickname();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.viewCount = board.getViewCount();
        this.category = board.getCategory();
        this.deadline = board.getDeadline();
        files = urlList;
    }
}
