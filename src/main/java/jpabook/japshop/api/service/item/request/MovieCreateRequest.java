package jpabook.japshop.api.service.item.request;

import jpabook.japshop.domain.item.Item;
import jpabook.japshop.domain.item.Movie;

public record MovieCreateRequest(
    String dType,
    String name,
    int price,
    int stockQuantity,
    String director,
    String actor
) implements ItemCreateRequest {

    @Override
    public Item toEntity() {
        return Movie.builder()
            .name(name)
            .price(price)
            .stockQuantity(stockQuantity)
            .director(director)
            .actor(actor)
            .build();
    }
}