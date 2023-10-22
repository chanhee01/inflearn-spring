package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") // 이거 하지 않으면 관례로 order로 들어감
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // FK(foreign key)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    //order에 의해서 mapping이 되었다는 뜻
    private List<OrderItem> orderItems = new ArrayList<>();
    //cascade는 persist 전파 (order만 persist해도 orderItems의 리스트도 같이 persist 됨)


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; // 주문 시간, 자바에서 기본으로 지원해주는 시간기능

    @Enumerated(EnumType.STRING) // Enum을 string으로 받아온다는 애노테이션
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL] (enum 타입)

    // 연관관계 메서드 //
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }
    /*
        Member member = new Member();
        Order order = new Order();

        member.getOrders().add(order);
        order.setMember(member);
        // 원래는 이렇게 적어야하는데 연관관계 메서드로 묶어주는 과정이다.
     */

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    // 연관관계 메서드 //
    // 양쪽에서 값을 불러올 수 있게 해주는 메서드 (db말고 객체의 입장에서도 사용할 수 있게)


    // 생성 메서드 // 주문을 생성을 할 때부터 createOrder이 호출 됨
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // 비즈니스 로직 //
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL); // 위의 예외가 안터지면 상태를 cancel로 바꿈
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }

    // 조회 로직 //
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
