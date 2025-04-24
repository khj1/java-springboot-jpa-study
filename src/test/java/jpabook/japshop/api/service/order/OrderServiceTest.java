package jpabook.japshop.api.service.order;

import jpabook.japshop.api.service.order.request.OrderCreateRequest;
import jpabook.japshop.api.service.order.response.OrderCreateResponse;
import jpabook.japshop.domain.common.Address;
import jpabook.japshop.domain.delivery.DeliveryStatus;
import jpabook.japshop.domain.item.Album;
import jpabook.japshop.domain.item.Item;
import jpabook.japshop.domain.item.ItemRepository;
import jpabook.japshop.domain.member.Member;
import jpabook.japshop.domain.member.MemberRepository;
import jpabook.japshop.domain.order.Order;
import jpabook.japshop.domain.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static jpabook.japshop.domain.order.OrderStatus.CANCELED;
import static jpabook.japshop.domain.order.OrderStatus.ORDER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @DisplayName("주문을 수행한다.")
    @Test
    void createOrder() {
        //given
        LocalDateTime now = LocalDateTime.of(2025, 4, 23, 0, 0);
        Address address = new Address("city", "street", "zipCode");

        Member member = createMember(address);
        Member savedMember = memberRepository.save(member);

        Album album = createAlbum(10);
        Album savedAlbum = itemRepository.save(album);

        OrderCreateRequest request = createOrderRequest(savedMember, savedAlbum, now);

        //when
        OrderCreateResponse response = orderService.createOrder(request);
        Order savedOrder = orderRepository.findById(response.orderId())
            .orElseThrow(() -> new AssertionError("주문이 저장되지 않았습니다."));

        //then
        assertAll(
            // response 상태 점검
            () -> assertThat(response).isNotNull(),
            () -> assertThat(response.orderId()).isEqualTo(savedOrder.getId()),

            // 주문 Entity 상태 점검
            () -> assertThat(savedOrder.getOrderItems()).hasSize(1),
            () -> assertThat(savedOrder.getOrderItems().get(0))
                .extracting("item", "orderPrice", "count")
                .contains(savedAlbum, 20_000, 2),
            () -> assertThat(savedOrder)
                .extracting("member", "orderDate", "orderStatus")
                .contains(savedMember, now, ORDER),
            () -> assertThat(savedOrder.getDelivery())
                .extracting("address", "deliveryStatus")
                .contains(address, DeliveryStatus.READY),

            // 재고 차감 로직 점검
            () -> assertThat(album.getStockQuantity()).isEqualTo(8)
        );
    }


    @DisplayName("재고 수량을 초과한 갯수를 주문하면 예외가 발생한다.")
    @Test
    void createOrderOverStockQuantity() {
        //given
        LocalDateTime now = LocalDateTime.of(2025, 4, 23, 0, 0);
        Address address = new Address("city", "street", "zipCode");

        Member member = createMember(address);
        Member savedMember = memberRepository.save(member);

        Album album = createAlbum(1);
        Album savedAlbum = itemRepository.save(album);

        OrderCreateRequest request = createOrderRequest(savedMember, savedAlbum, now);

        //when //then
        assertThatThrownBy(() -> orderService.createOrder(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("재고가 부족합니다.");
    }

    @DisplayName("주문을 취소한다.")
    @Test
    void cancelOrder() {
        //given
        LocalDateTime now = LocalDateTime.of(2025, 4, 23, 0, 0);
        Address address = new Address("city", "street", "zipCode");

        Member member = createMember(address);
        Member savedMember = memberRepository.save(member);

        Album album = createAlbum(10);
        Album savedAlbum = itemRepository.save(album);

        OrderCreateRequest request = createOrderRequest(savedMember, savedAlbum, now);
        OrderCreateResponse response = orderService.createOrder(request);

        //when
        orderService.cancelOrder(response.orderId());

        //then
        Order cancelledOrder = orderRepository.findById(response.orderId())
            .orElseThrow(() -> new AssertionError("주문이 존재하지 않습니다."));
        Item item = itemRepository.findById(savedAlbum.getId())
            .orElseThrow(() -> new AssertionError("상품이 존재하지 않습니다."));

        assertAll(
            () -> assertThat(cancelledOrder.getOrderStatus()).isEqualTo(CANCELED),
            () -> assertThat(item.getStockQuantity()).isEqualTo(10)
        );
    }

    @DisplayName("주문을 검색한다.")
    @Test
    void searchOrder() {
        //given

        //when

        //then
    }

    private static Album createAlbum(int stockQuantity) {
        return Album.builder()
            .name("앨범")
            .price(10_000)
            .stockQuantity(stockQuantity)
            .build();
    }

    private static Member createMember(Address address) {
        return Member.builder()
            .name("유저")
            .address(address)
            .build();
    }

    private static OrderCreateRequest createOrderRequest(Member member, Item item, LocalDateTime now) {
        return new OrderCreateRequest(
            member.getId(),
            item.getId(),
            2,
            now
        );
    }
}