package jpabook.japshop.api.service.item.response;

public sealed interface ItemResponse
    permits AlbumResponse, BookResponse, MovieResponse {

    Long id();

    String name();

    int price();

    int stockQuantity();
}
