package capstone.ApplePie_Spring.Board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BoardUpdateDto {
    private Long userId;

    private String title;
    private String content;
    private int category;
    private LocalDate deadline;
}
