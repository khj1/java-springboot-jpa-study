package jpabook.japshop.api.service.item.mapper;

import jpabook.japshop.api.service.item.response.AlbumResponse;
import jpabook.japshop.api.service.item.response.BookResponse;
import jpabook.japshop.api.service.item.response.ItemResponse;
import jpabook.japshop.api.service.item.response.MovieResponse;
import jpabook.japshop.domain.item.Album;
import jpabook.japshop.domain.item.Book;
import jpabook.japshop.domain.item.Item;
import jpabook.japshop.domain.item.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;

@Mapper(
    componentModel = "spring",
    subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION
)
public interface ItemMapper {

    @SubclassMapping(source = Book.class, target = BookResponse.class)
    @SubclassMapping(source = Album.class, target = AlbumResponse.class)
    @SubclassMapping(source = Movie.class, target = MovieResponse.class)
    ItemResponse toResponse(Item item);

    BookResponse toResponse(Book book);

    AlbumResponse toResponse(Album album);

    MovieResponse toResponse(Movie movie);

}
