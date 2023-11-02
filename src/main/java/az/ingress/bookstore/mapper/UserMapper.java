//package az.ingress.bookstore.mapper;
//
//import az.ingress.bookstore.dao.entity.Author;
//import az.ingress.bookstore.dao.entity.Student;
//import az.ingress.bookstore.dao.entity.UserEntity;
//import az.ingress.bookstore.model.request.RegisterRequest;
//import az.ingress.bookstore.model.response.RegisterResponse;
//import org.mapstruct.Mapper;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface UserMapper {
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//
//    UserEntity fromRequestToModel(RegisterRequest request);
//    RegisterResponse fromModelToResponse(UserEntity userEntity);
//
//    Author fromRequestToModel2(RegisterRequest request);
//    RegisterResponse fromModelToResponse2(Author author );
//    Student fromRequestToModel3(RegisterRequest request);
//    RegisterResponse fromModelToResponse3(Student student );
//
//}
