package capstone.ApplePie_Spring.Profiles.dto;

import capstone.ApplePie_Spring.Board.domain.Board;
import lombok.Getter;

import javax.annotation.Nullable;

@Getter
public class OneProfilesFindDto {
    private Board.Category category;
    private Long oid; // open profile
}
