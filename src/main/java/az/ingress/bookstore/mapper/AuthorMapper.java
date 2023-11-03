package az.ingress.bookstore.mapper;

import az.ingress.bookstore.dao.entity.Author;
import az.ingress.bookstore.dto.request.AuthorRequest;
import az.ingress.bookstore.dto.request.RegisterRequest;
import az.ingress.bookstore.dto.response.AuthorResponse;
import az.ingress.bookstore.dto.response.RegisterResponse;
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
    AuthorResponse fromModelToResponse1(Author author);
    Author fromRequestToModel(AuthorRequest request);
}
