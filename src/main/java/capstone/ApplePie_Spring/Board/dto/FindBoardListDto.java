package capstone.ApplePie_Spring.Board.dto;

import capstone.ApplePie_Spring.Board.domain.Board;
import capstone.ApplePie_Spring.Board.domain.File;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Getter
public class FindBoardListDto {

    //private String nickname;
    private Long id;
    private String title;
    private String content;
    private int viewCount;
    private Board.Category categoryId;
    private String file;

    private LocalDate deadline;
    private boolean status; // 만료 여부

    @Builder
    public FindBoardListDto(Board board, Optional<File> file) {
        this.id = board.getId();
        //this.nickname = board.getUser().getNickname();
        this.categoryId = board.getCategory();

        this.title = board.getTitle();
        this.viewCount = board.getViewCount();

        this.deadline = board.getDeadline();
        this.status = LocalDate.now().compareTo(board.getDeadline()) <= 1;

        if (board.getFiles().size() == 0) {
            this.file = null;
        }
        else {
            this.file = board.getFiles().get(0).getUrl();
        }

        if (board.getContent().length() > 16) {
            this.content = board.getContent().substring(0,14); // 15글자
        }
        else {
            this.content = board.getContent();
        }

        if (file.isEmpty()) {
            this.file = null;
        }
        else {
            this.file = file.get().getUrl();
        }
    }
}
