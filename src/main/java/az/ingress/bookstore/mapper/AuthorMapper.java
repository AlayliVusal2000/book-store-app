package az.ingress.bookstore.mapper;

import az.ingress.bookstore.dao.entity.Author;
import az.ingress.bookstore.model.request.RegisterRequest;
import az.ingress.bookstore.model.response.RegisterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {

    AuthorMapper AUTHOR_MAPPER = Mappers.getMapper(AuthorMapper.class);

    Author fromRequestToModel(RegisterRequest request);

    RegisterResponse fromModelToResponse(Author author);
}
