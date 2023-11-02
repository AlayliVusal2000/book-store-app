package az.ingress.bookstore.mapper;

import az.ingress.bookstore.dao.entity.Student;
import az.ingress.bookstore.model.request.RegisterRequest;
import az.ingress.bookstore.model.response.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    StudentMapper STUDENT_MAPPER = Mappers.getMapper(StudentMapper.class);
    Student fromRequestToModel(RegisterRequest request);
    RegisterResponse fromModelToResponse(Student student );

}
