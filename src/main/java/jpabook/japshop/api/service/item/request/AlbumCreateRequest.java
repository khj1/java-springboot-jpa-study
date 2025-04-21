package jpabook.japshop.api.service.item.request;

import jpabook.japshop.domain.item.Album;
import jpabook.japshop.domain.item.Item;

public record AlbumCreateRequest(
    String dType,
    String name,
    int price,
    int stockQuantity,
    String artist,
    String etc
) implements ItemCreateRequest {

    @Override
    public Item toEntity() {
        return Album.builder()
            .name(name)
            .price(price)
            .stockQuantity(stockQuantity)
            .artist(artist)
            .etc(etc)
            .build();
    }
}