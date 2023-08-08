package capstone.ApplePie_Spring.Team.repository;

import capstone.ApplePie_Spring.Team.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member save(Member member);
    List<Member> findAllByTeamIdAndStatus(Long teamId, int status);
}
