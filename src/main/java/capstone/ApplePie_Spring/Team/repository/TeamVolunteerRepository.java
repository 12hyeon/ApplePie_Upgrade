package capstone.ApplePie_Spring.Team.repository;

import capstone.ApplePie_Spring.Team.domain.TeamVolunteer;
import capstone.ApplePie_Spring.Team.domain.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamVolunteerRepository extends JpaRepository<Volunteer, Long> {
    TeamVolunteer save(TeamVolunteer teamVolunteer);
    List<TeamVolunteer> findAllByStatus(int status);
}
