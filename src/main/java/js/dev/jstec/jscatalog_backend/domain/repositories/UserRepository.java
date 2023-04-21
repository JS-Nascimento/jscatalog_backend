package js.dev.jstec.jscatalog_backend.domain.repositories;

import js.dev.jstec.jscatalog_backend.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
