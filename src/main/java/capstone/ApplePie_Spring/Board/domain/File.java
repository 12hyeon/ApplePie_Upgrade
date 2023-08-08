package capstone.ApplePie_Spring.Board.domain;

import capstone.ApplePie_Spring.Board.dto.FileDto;
import capstone.ApplePie_Spring.config.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "FILE")
public class File extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private int number;
    private String name;
    private long size;
    private String extension;
    private String url;

    // 연관 관계 매핑
    @JsonIgnore
    @ManyToOne
    private Board board;

    @Builder
    public File(FileDto fileDto, Board board) {
        this.name = fileDto.getName();
        this.size = fileDto.getSize();
        this.extension = fileDto.getExtension();
        this.number = fileDto.getNumber();
        this.url = fileDto.getUrl();

        this.board = board;
        board.getFiles().add(this);
    }

    public void delete() {
        super.delete();
    }

}
