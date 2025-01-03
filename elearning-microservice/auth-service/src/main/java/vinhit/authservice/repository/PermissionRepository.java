package vinhit.authservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import vinhit.authservice.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission,Long> {

}
