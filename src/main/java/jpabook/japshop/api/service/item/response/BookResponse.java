package jpabook.japshop.api.service.item.response;

public record BookResponse(
    Long id,
    String name,
    int price,
    int stockQuantity,
    String author,
    String isbn
) implements ItemResponse {
}
