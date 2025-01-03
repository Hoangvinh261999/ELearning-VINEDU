package vinhit.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vinhit.authservice.entity.TeacherProfile;

public interface TeacherRepository extends JpaRepository<TeacherProfile,Long> {
    TeacherProfile findTeacherProfileByUserid(Long userid);

}
