package capstone.ApplePie_Spring.AI.repository;

import capstone.ApplePie_Spring.AI.domain.AI;
import capstone.ApplePie_Spring.AI.dto.categoryFind;
import capstone.ApplePie_Spring.Board.domain.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIRepository extends JpaRepository<AI, Long> {
    //List<categoryFind> findAllById(Long user_id);
    AI save(AI ai);
    List<AI> findAllByUserIdAndCategory(long user_id, AI.Category category);
}
