package jpabook.japshop.domain.order;

import jakarta.persistence.*;
import jpabook.japshop.domain.item.Item;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;

    private int count;

    private OrderItem(Item item, int count) {
        this.item = item;
        this.count = count;
        this.orderPrice = calculateOrderPrice(item, count);
    }

    private int calculateOrderPrice(Item item, int count) {
        return item.getPrice() * count;
    }

    public static OrderItem create(Item item, int count) {
        item.removeStock(count);
        return new OrderItem(item, count);
    }

    public void linkToOrder(Order order) {
        this.order = order;
    }
}
