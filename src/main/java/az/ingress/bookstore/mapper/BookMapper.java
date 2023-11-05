package az.ingress.bookstore.mapper;

import az.ingress.bookstore.dao.entity.Book;
import az.ingress.bookstore.dto.request.BookRequest;
import az.ingress.bookstore.dto.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface BookMapper {
    BookMapper BOOK_MAPPER = Mappers.getMapper(BookMapper.class);

    Book fromRequestToModel(BookRequest request);

    BookResponse fromModelToResponse(Book book);
    List<BookResponse> fromListModelToListResponse(List<Book> books);
}
