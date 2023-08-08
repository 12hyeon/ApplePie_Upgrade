package capstone.ApplePie_Spring.Profiles.repository;

import capstone.ApplePie_Spring.Profiles.domain.Lesson;
import capstone.ApplePie_Spring.Profiles.domain.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project save(Project project);
    Optional<Project> findByProfileIdAndStatus(Long profileId, int status);
    Optional<Project> findByIdAndStatus(Long id, int status);
    boolean existsByProfileIdAndStatus(Long profileId, int status);
    List<Project> findAllByStatusAndOpenOrderByIdDesc(int status, boolean open, Pageable pageable);
    List<Project> findAllByIdLessThanAndStatusAndOpenOrderByIdDesc(long id, int status, boolean open, Pageable pageable);
}