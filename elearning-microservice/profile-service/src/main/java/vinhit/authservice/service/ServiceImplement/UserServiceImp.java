package vinhit.authservice.service.ServiceImplement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vinhit.authservice.dto.request.CreateDefaultUserProfileRequest;
import vinhit.authservice.dto.response.UserResponse;
import vinhit.authservice.entity.StudentProfile;
import vinhit.authservice.entity.UserProfile;
import vinhit.authservice.mapper.UserDefaultCreateProfile;
import vinhit.authservice.repository.StudentRepository;
import vinhit.authservice.repository.TeacherRepository;
import vinhit.authservice.service.UserService;

import java.util.List;

@Service
@Slf4j
public class UserServiceImp implements UserService {
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    UserDefaultCreateProfile userDefaultCreateProfile;

    @Override
    public boolean userCreate(CreateDefaultUserProfileRequest createUserProfileRequest) {
        try {
            UserProfile userProfile = userDefaultCreateProfile.toUser(createUserProfileRequest);

            StudentProfile studentProfile = userDefaultCreateProfile.toStudentProfile(createUserProfileRequest, userProfile);
            studentRepository.save(studentProfile);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public UserProfile findprofile(Long id, String where) {
        switch (where) {
            case "Student":
                return studentRepository.findStudentProfileByUserid(id);
            case "Teacher":
                return teacherRepository.findTeacherProfileByUserid(id);
            default:
                break;
        }
        return null;

    }

    @Override
    public UserResponse getUser(Long usId) {
        return null;
    }

    @Override
    public UserResponse getMyInformation(Long usId) {
        return null;
    }

    @Override
    public List<UserResponse> getUsers() {
        return null;
    }

    @Override
    public void updateUser() {

    }

    @Override
    public void deleteUser() {

    }
}
