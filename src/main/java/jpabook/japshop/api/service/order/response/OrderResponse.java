package jpabook.japshop.api.service.order.response;

import jpabook.japshop.domain.common.Address;
import jpabook.japshop.domain.order.OrderStatus;

import java.time.LocalDateTime;

public record OrderResponse(
    Long orderId,
    String memberName,
    LocalDateTime orderDate,
    OrderStatus orderStatus,
    Address address
) {
}
