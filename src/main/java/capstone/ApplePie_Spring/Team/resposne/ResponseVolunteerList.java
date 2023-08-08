package capstone.ApplePie_Spring.Team.resposne;

import capstone.ApplePie_Spring.Team.domain.Volunteer;
import capstone.ApplePie_Spring.Team.dto.VolunteerStatusDto;
import capstone.ApplePie_Spring.config.ResponseType;
import capstone.ApplePie_Spring.validation.ExceptionCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ResponseVolunteerList extends ResponseType {

    private List<Integer> totalCount;
    private List<Integer> count;
    private List<VolunteerStatusDto> volunteerList;
    private List<VolunteerStatusDto> memberList;


    public ResponseVolunteerList(ExceptionCode exceptionCode, List<Volunteer> volunteers,
                                 List<Integer> count, List<Integer> totalCount) {
        super(exceptionCode);
        volunteerList = new ArrayList<>();
        memberList = new ArrayList<>();
        for(Volunteer v: volunteers) {
            if (v.getVolunteerStatus().equals(Volunteer.VolunteerStatus.APPLY)) {
                volunteerList.add(new VolunteerStatusDto(v.getId(), v.getRole(), v.getVolunteerStatus()));
            }
            else {
                memberList.add(new VolunteerStatusDto(v.getId(), v.getRole(), v.getVolunteerStatus()));
            }
        }
        this.count = count;
        this.totalCount = totalCount;
    }
}
