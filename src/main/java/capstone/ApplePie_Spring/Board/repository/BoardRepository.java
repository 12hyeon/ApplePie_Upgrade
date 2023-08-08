package capstone.ApplePie_Spring.Board.repository;

import capstone.ApplePie_Spring.Board.domain.Board;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board save(Board board);
    Optional<Board> findByIdAndStatus(Long id, int status);
    boolean existsByIdAndStatus(Long id, int status);

    List<Board> findAllByUserIdAndStatusAndDeadlineGreaterThanEqualOrderByIdDesc(Long userId, int status, LocalDate deadline);

    List<Board> findAllByCategoryAndStatusAndDeadlineGreaterThanEqualOrderByIdDesc(Board.Category category, int status, LocalDate deadline, Pageable pageable);
    List<Board> findAllByCategoryAndStatusAndTitleContainingAndDeadlineGreaterThanEqualOrderByIdDesc(Board.Category category, String title, int status, LocalDate deadline, Pageable pageable);
    List<Board> findAllByIdLessThanAndCategoryAndStatusAndDeadlineGreaterThanEqualOrderByIdDesc(long id, Board.Category category, int status, LocalDate deadline, Pageable pageable);
    List<Board> findAllByIdLessThanAndCategoryAndStatusAndTitleContainingAndDeadlineGreaterThanEqualOrderByIdDesc(long id, Board.Category category, int status, String title, LocalDate deadline, Pageable pageable);

    List<Board> findAllByCategoryAndStatusAndDeadlineGreaterThanEqual(Board.Category category, LocalDate deadline, int status);

    @Query(value = "select * from Board b where b.category = ?1 and b.status = ?2 order by b.view_count desc limit 3" , nativeQuery = true)
    List<Board> ViewCount(Board.Category category, int status);

    List<Board> findAllByCategoryAndStatusAndDeadlineGreaterThanEqualOrderByViewCountDesc(Board.Category category, LocalDate deadline, int status);
}
