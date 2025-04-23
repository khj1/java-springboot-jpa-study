package jpabook.japshop.api.service.order.response;

public record OrderCreateResponse(
    Long itemId
) {
    public static OrderCreateResponse of(Long id) {
        return new OrderCreateResponse(id);
    }
}
