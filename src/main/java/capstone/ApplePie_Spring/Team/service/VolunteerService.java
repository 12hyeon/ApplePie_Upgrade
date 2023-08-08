package capstone.ApplePie_Spring.Team.service;

import capstone.ApplePie_Spring.Team.dto.CancelVolunteerDto;
import capstone.ApplePie_Spring.Team.dto.FindVolunteerDto;
import capstone.ApplePie_Spring.Team.dto.VolunteerDto;

public interface VolunteerService {
    Object save(VolunteerDto volunteerDto);
    Object cancel(CancelVolunteerDto cancelVolunteerDto);
    Object getVolunteers(Long uid, FindVolunteerDto findVolunteerDto);
}
