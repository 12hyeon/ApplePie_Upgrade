package capstone.ApplePie_Spring.Team.domain;

import capstone.ApplePie_Spring.Board.domain.Board;
import capstone.ApplePie_Spring.User.domain.User;
import capstone.ApplePie_Spring.config.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "TEAM_VOLUNTEER")
public class TeamVolunteer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String title;
    private String content;

    private Board.Category category;

    private Volunteer.Role role;

    public void delete() {
        super.delete();
    }

    @Builder
    public TeamVolunteer(User user, Board board, Volunteer.Role role) {
        this.userId = user.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.category = board.getCategory();
        this.role = role;
    }

}
