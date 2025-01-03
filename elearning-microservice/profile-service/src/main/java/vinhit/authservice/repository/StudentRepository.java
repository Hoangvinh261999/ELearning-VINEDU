package vinhit.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vinhit.authservice.entity.StudentProfile;
import vinhit.authservice.entity.UserProfile;

public interface StudentRepository extends JpaRepository<StudentProfile,Long> {
    UserProfile findStudentProfileByUserid(Long id);

}
