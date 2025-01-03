package vinhit.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vinhit.authservice.entity.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
   Role findByRoleName(vinhit.authservice.enums.Role role);

}
