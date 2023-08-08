package capstone.ApplePie_Spring.Board.domain;

import capstone.ApplePie_Spring.Board.dto.BoardSaveDto;
import capstone.ApplePie_Spring.Board.dto.BoardUpdateDto;
import capstone.ApplePie_Spring.config.BaseEntity;
import capstone.ApplePie_Spring.Team.domain.Team;
import capstone.ApplePie_Spring.User.domain.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(name = "BOARD")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DynamicInsert
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 300)
    private String content;

    private int viewCount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate deadline;

    // 연관 관계 매핑

    @JsonIgnore
    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "board")
    private Team team;

    public enum Category {
        OUTSOURCING(0,"외주"),
        LESSON(1,"과외"),
        PROJECT(2, "공모전");

        private int id;
        private String name;

        Category(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static Category getValue(int id) {
            return Arrays.stream(values())
                    .filter(value -> value.id == id)
                    .findAny()
                    .orElse(null);
        }
    }

    private Category category; // 단방향 매핑

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<File> files = new ArrayList<>();

    @Builder
    public Board(BoardSaveDto boardSaveDto, User user) {
        this.title = boardSaveDto.getTitle();
        this.content = boardSaveDto.getContent();
        this.viewCount = 0;
        this.category = boardSaveDto.getCategory();
        this.deadline = boardSaveDto.getDeadline();

        this.user = user;
    }

    public void delete() {
        for (File file : files) {
            file.delete();
        }
        files.clear();
        this.team.delete();
        super.delete();
    }

    public void addViewCount() {
        this.viewCount += 1;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void update(BoardUpdateDto boardUpdateDto) {
        this.title = boardUpdateDto.getTitle();
        this.content = boardUpdateDto.getContent();
        this.category = Category.getValue(boardUpdateDto.getCategory());
        this.deadline = boardUpdateDto.getDeadline();

        for (File file : files) {
            file.delete();
        }
        files.clear();
    }
}
