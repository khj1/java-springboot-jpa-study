package jpabook.japshop.api.service.item.response;

public record MovieResponse(
    Long id,
    String name,
    int price,
    int stockQuantity,
    String director,
    String actor
) implements ItemResponse {
}