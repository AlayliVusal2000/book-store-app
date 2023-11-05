package az.ingress.bookstore.mapper;

import az.ingress.bookstore.dao.entity.Student;
import az.ingress.bookstore.dto.request.RegisterRequest;
import az.ingress.bookstore.dto.response.RegisterResponse;
import az.ingress.bookstore.dto.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    StudentMapper STUDENT_MAPPER = Mappers.getMapper(StudentMapper.class);

    Student fromRequestToModel(RegisterRequest request);

    RegisterResponse fromModelToRegisterResponse(Student student);

    StudentResponse fromModelToResponse(Student student);

}
