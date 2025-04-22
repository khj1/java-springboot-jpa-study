package jpabook.japshop.api.service.item.response;

public record AlbumResponse(
    Long id,
    String name,
    int price,
    int stockQuantity,
    String artist,
    String etc
) implements ItemResponse {
}