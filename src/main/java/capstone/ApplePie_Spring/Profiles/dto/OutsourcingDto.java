package capstone.ApplePie_Spring.Profiles.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OutsourcingDto {
    private String introduce;
    private String career;
    private String outsourcingSelf;
    private boolean open;

    @Builder
    public OutsourcingDto(String career, String outsourcingSelf, String introduce, boolean open) {
        this.career = career;
        this.outsourcingSelf = outsourcingSelf;
        this.open = open;
        this.introduce = introduce;

    }
}
