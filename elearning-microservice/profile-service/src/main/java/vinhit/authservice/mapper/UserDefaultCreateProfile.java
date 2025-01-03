
package vinhit.authservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Service;
import vinhit.authservice.dto.request.CreateDefaultUserProfileRequest;
import vinhit.authservice.dto.response.UserResponse;
import vinhit.authservice.entity.StudentProfile;
import vinhit.authservice.entity.UserProfile;
@Service
@Mapper(componentModel = "Spring")
public interface UserDefaultCreateProfile {
    //    UserMapper userMapper= Mappers.getMapper(UserMapper.class);
    @Mapping(target = "userid",source = "userid")
    UserProfile toUser(CreateDefaultUserProfileRequest request);

    @Mapping(target = "userid", source = "request.userid")
    StudentProfile toStudentProfile(CreateDefaultUserProfileRequest request, UserProfile userProfile);

    UserResponse toUserCreateResponse(UserProfile userProfile);


}

