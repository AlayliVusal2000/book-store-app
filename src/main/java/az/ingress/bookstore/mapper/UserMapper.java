package az.ingress.bookstore.mapper;

import az.ingress.bookstore.dao.entity.UserEntity;
import az.ingress.bookstore.model.request.RegisterRequest;
import az.ingress.bookstore.model.response.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity fromRequestToModel(RegisterRequest request);
    RegisterResponse fromModelToResponse(UserEntity userEntity);

}
