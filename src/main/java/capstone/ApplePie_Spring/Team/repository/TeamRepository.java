package capstone.ApplePie_Spring.Team.repository;

import capstone.ApplePie_Spring.Team.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Team save(Team team);
    Optional<Team> findByBoardIdAndStatus(Long boardId, int status);
    Optional<Team> findByIdAndStatus(Long id, int status);
    List<Team> findAllByUserIdAndStatus(Long userId, int status);
    List<Team> findAllByUserIdAndTeamStatusAndStatus(Long userId, Team.TeamStatus teamStatus, int status);
    Optional<Team> findByIdAndTeamStatusAndStatus(Long id, Team.TeamStatus teamStatus, int status);
}
