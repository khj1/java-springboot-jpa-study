package jpabook.japshop.api.service.order;

import jpabook.japshop.api.service.order.request.OrderCreateRequest;
import jpabook.japshop.api.service.order.request.OrderSearchCondition;
import jpabook.japshop.api.service.order.response.OrderCreateResponse;
import jpabook.japshop.api.service.order.response.OrderResponse;
import jpabook.japshop.domain.delivery.Delivery;
import jpabook.japshop.domain.item.Item;
import jpabook.japshop.domain.item.ItemRepository;
import jpabook.japshop.domain.member.Member;
import jpabook.japshop.domain.member.MemberRepository;
import jpabook.japshop.domain.order.Order;
import jpabook.japshop.domain.order.OrderItem;
import jpabook.japshop.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jpabook.japshop.domain.delivery.DeliveryStatus.READY;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public OrderCreateResponse createOrder(OrderCreateRequest request) {
        Member member = findMember(request);
        Item item = findItem(request);

        OrderItem orderItem = OrderItem.create(item, request.count());
        Delivery delivery = Delivery.of(member.getAddress(), READY);

        Order order = Order.create(member, delivery, request.orderDate(), orderItem);
        Order savedOrder = orderRepository.save(order);

        return OrderCreateResponse.of(savedOrder.getId());
    }

    private Item findItem(OrderCreateRequest request) {
        return itemRepository.findById(request.itemId())
            .orElseThrow(() -> new IllegalArgumentException("상품 정보가 존재하지 않습니다."));
    }

    private Member findMember(OrderCreateRequest request) {
        return memberRepository.findById(request.memberId())
            .orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));

        order.cancel();
    }

    public List<OrderResponse> findAllBy(OrderSearchCondition orderSearchCondition) {
        return orderRepository.findAll(orderSearchCondition);
    }
}
