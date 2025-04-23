package jpabook.japshop.domain.order;

import jakarta.persistence.*;
import jpabook.japshop.domain.delivery.Delivery;
import jpabook.japshop.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jpabook.japshop.domain.order.OrderStatus.ORDER;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Table(name = "orders")
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private Order(Member member, Delivery delivery, LocalDateTime orderDate) {
        this.member = member;
        this.delivery = delivery;
        this.orderDate = orderDate;
        this.orderStatus = ORDER;
    }

    public static Order create(Member member, Delivery delivery, LocalDateTime orderDate, OrderItem... orderItems) {
        Order order = new Order(member, delivery, orderDate);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    private void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.linkToOrder(this);
    }
}
