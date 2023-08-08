package capstone.ApplePie_Spring.Profiles.dto;

import capstone.ApplePie_Spring.Board.domain.Board;
import lombok.Getter;

@Getter
public class OpenDto {
    private boolean open;
    private Board.Category category;
}
