package jpabook.japshop.api.service.item.request;

import jpabook.japshop.domain.item.Book;
import jpabook.japshop.domain.item.Item;

public record BookCreateRequest(
    String dType,
    String name,
    int price,
    int stockQuantity,
    String author,
    String isbn
) implements ItemCreateRequest {

    @Override
    public Item toEntity() {
        return Book.builder()
            .name(name)
            .price(price)
            .stockQuantity(stockQuantity)
            .author(author)
            .isbn(isbn)
            .build();
    }
}
