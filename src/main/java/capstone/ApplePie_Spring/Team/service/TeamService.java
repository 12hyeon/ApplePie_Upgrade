package capstone.ApplePie_Spring.Team.service;

import capstone.ApplePie_Spring.Team.dto.TeamDto;
import capstone.ApplePie_Spring.Team.dto.TeamUpdateDto;

public interface TeamService {
    Object save(Long uid, TeamDto teamDto);
    Object complete(TeamUpdateDto teamUpdateDto);
    Object update(Long uid, TeamDto teamDto);
    Object cancel(Long uid, TeamUpdateDto teamUpdateDto);
    Object delete(Long uid, TeamUpdateDto teamUpdateDto);

    Object findTeam(Long userId);
}
