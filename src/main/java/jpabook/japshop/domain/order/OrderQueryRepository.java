package jpabook.japshop.domain.order;

import jpabook.japshop.api.service.order.request.OrderSearchCondition;
import jpabook.japshop.api.service.order.response.OrderResponse;

import java.util.List;

public interface OrderQueryRepository {
    List<OrderResponse> findAll(OrderSearchCondition orderSearchCondition);
}
