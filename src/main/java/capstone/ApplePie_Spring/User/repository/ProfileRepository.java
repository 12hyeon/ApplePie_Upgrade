package capstone.ApplePie_Spring.User.repository;

import capstone.ApplePie_Spring.User.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByIdAndStatus(Long id, int status);
    Optional<Profile> findByUserIdAndStatus(Long userId, int status);
    boolean existsByIdAndStatus(Long id, int status);
    Profile save(Profile profile);
}
