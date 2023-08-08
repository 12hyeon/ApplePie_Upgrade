package capstone.ApplePie_Spring.Profiles.repository;

import capstone.ApplePie_Spring.Profiles.domain.Lesson;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Lesson save(Lesson lesson);
    Optional<Lesson> findByProfileIdAndStatus(Long profileId, int status);
    Optional<Lesson> findByIdAndStatus(Long id, int status);
    boolean existsByProfileIdAndStatus(Long profileId, int status);
    List<Lesson> findAllByStatusAndOpenOrderByIdDesc(int status, boolean open, Pageable pageable);
    List<Lesson> findAllByIdLessThanAndStatusAndOpenOrderByIdDesc(long id, int status, boolean open, Pageable pageable);
}
