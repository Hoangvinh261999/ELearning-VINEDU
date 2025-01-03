package vinhit.authservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vinhit.authservice.dto.request.CreateUserRequest;
import vinhit.authservice.dto.response.UserResponse;
import vinhit.authservice.entity.User;

@Mapper(componentModel = "Spring")
public interface UserMapper {
//    UserMapper userMapper= Mappers.getMapper(UserMapper.class);
    @Mapping(source = "username", target = "username")
    User toUser(CreateUserRequest request);
    UserResponse toUserCreateResponse(User user);

}
