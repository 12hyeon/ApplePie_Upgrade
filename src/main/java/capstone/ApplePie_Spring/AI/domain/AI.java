package capstone.ApplePie_Spring.AI.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Arrays;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "AI")
public class AI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;

    private Long userId;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 300)
    private String content;

    private int role;

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

        public static AI.Category getValue(int id) {
            return Arrays.stream(values())
                    .filter(value -> value.id == id)
                    .findAny()
                    .orElse(null);
        }

    }

    private Category category;

    /*
    @Column(nullable = false)
    private String category;
     */
}
