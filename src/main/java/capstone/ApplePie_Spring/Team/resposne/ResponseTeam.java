package capstone.ApplePie_Spring.Team.resposne;

import capstone.ApplePie_Spring.Team.domain.Team;
import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseTeam extends ResponseType {

    @JsonIgnore
    private Team team;

    public ResponseTeam(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public ResponseTeam(ExceptionCode exceptionCode, Team team) {
        super(exceptionCode);
        this.team = team;

    }
}
