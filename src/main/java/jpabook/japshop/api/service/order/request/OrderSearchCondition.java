package jpabook.japshop.api.service.order.request;

import jpabook.japshop.domain.order.OrderStatus;

public record OrderSearchCondition(
    String memberName,
    OrderStatus status
) {
}
