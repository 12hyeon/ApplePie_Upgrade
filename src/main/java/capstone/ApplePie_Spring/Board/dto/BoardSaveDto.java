package capstone.ApplePie_Spring.Board.dto;

import capstone.ApplePie_Spring.Board.domain.Board;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BoardSaveDto {
    private Long userId;

    private String title;
    private String content;
    private Board.Category category;
    private LocalDate deadline;
}
