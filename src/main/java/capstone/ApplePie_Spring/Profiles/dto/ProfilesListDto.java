package capstone.ApplePie_Spring.Profiles.dto;

import capstone.ApplePie_Spring.Board.domain.Board;
import capstone.ApplePie_Spring.Profiles.domain.Lesson;
import capstone.ApplePie_Spring.Profiles.domain.Outsourcing;
import capstone.ApplePie_Spring.Profiles.domain.Project;
import capstone.ApplePie_Spring.User.domain.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfilesListDto {
    private Long oid;
    private String introduce;
    private Board.Category category;

    @Builder
    public ProfilesListDto(Long id, String introduce, Board.Category category) {
        this.oid = id;
        this.introduce = introduce;
        this.category = category;
    }
}
