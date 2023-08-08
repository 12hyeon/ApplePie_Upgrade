package capstone.ApplePie_Spring.Profiles.repository;

import capstone.ApplePie_Spring.Profiles.domain.Lesson;
import capstone.ApplePie_Spring.Profiles.domain.Outsourcing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OutsourcingRepository extends JpaRepository<Outsourcing, Long> {
    Outsourcing save(Outsourcing outsourcing);
    Optional<Outsourcing> findByProfileIdAndStatus(Long profileId, int status);
    Optional<Outsourcing> findByIdAndStatus(Long id, int status);
    boolean existsByProfileIdAndStatus(Long profileId, int status);
    List<Outsourcing> findAllByStatusAndOpenOrderByIdDesc(int status, boolean open, Pageable pageable);
    List<Outsourcing> findAllByIdLessThanAndStatusAndOpenOrderByIdDesc(long id, int status, boolean open, Pageable pageable);
}