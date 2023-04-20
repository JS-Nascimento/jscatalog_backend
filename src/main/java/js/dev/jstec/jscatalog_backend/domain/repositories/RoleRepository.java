package js.dev.jstec.jscatalog_backend.domain.repositories;

import js.dev.jstec.jscatalog_backend.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


}
