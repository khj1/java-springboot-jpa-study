package jpabook.japshop.api.service.order.response;

public record OrderCreateResponse(
    Long orderId
) {
    public static OrderCreateResponse of(Long id) {
        return new OrderCreateResponse(id);
    }
}
