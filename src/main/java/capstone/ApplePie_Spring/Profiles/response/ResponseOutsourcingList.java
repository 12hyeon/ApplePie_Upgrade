package capstone.ApplePie_Spring.Profiles.response;

import capstone.ApplePie_Spring.Board.domain.Board;
import capstone.ApplePie_Spring.Profiles.domain.Lesson;
import capstone.ApplePie_Spring.Profiles.domain.Outsourcing;
import capstone.ApplePie_Spring.Profiles.domain.Project;
import capstone.ApplePie_Spring.Profiles.dto.ProfilesListDto;
import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ResponseOutsourcingList extends ResponseType {
    private List<ProfilesListDto> data;

    public ResponseOutsourcingList(ExceptionCode exceptionCode, List<Outsourcing> outsourcings) {
        super(exceptionCode);
        this.data = new ArrayList<>();
        for (Outsourcing outsourcing : outsourcings) {
            data.add(ProfilesListDto.builder()
                    .id(outsourcing.getId())
                    .introduce(outsourcing.getIntroduce())
                    .category(Board.Category.OUTSOURCING)
                    .build());
        }
    }
}
