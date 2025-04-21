package jpabook.japshop.api.service.item.response;

public record ItemCreateResponse(
    Long itemId
) {
    public static ItemCreateResponse of(Long id) {
        return new ItemCreateResponse(id);
    }
}
