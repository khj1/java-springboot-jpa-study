package jpabook.japshop.api.service.order.request;

import java.time.LocalDateTime;

public record OrderCreateRequest(
    Long memberId,
    Long itemId,
    int count,
    LocalDateTime orderDate
) {
}
