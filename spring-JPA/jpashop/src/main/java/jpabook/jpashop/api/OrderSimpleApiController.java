package jpabook.jpashop.api;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * xToOne(ManyToOne, OneToOne)만 해당됨
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private EntityManager em;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() { // 엔티티를 외부로 노출하면 안됨
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); // LAZY 강제 초기화
            order.getDelivery().getAddress(); // LAZY 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/v2/simple-orders") // 지연로딩으로 인한 쿼리가 너무 많이 호출됨
    public List<SimpleOrderDto> ordersV2() { // 주의) list로 반환하면 안되고 result로 감싸야함!!!!!!!!!!
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        // 이걸 그대로 반환하지말고 Dto로 바꿔서 반환해야함 -> 아래 과정에서 Dto로 바꿈

        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                // 이거는 map(a -> b)  ---- a를 b로 바꾸는거
                .collect(Collectors.toList());

        // 여기서 루프돌 때 각각 주문마다 member, delivery 쿼리를 내보냄
        // 1 + N이라는 공식
        // order -> member 지연 로딩 조회 N번
        // order -> delivery 지연 로딩 조회 N번
        // N은 2이고 1 + member개수(2) + delivery개수(2) = 5
        // 만약에 한 회원이 주문 2개면 1 + 1 + 2가 된다.

        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
        // V2와 컨트롤러는 비슷한데 쿼리가 다름 (Repository에서 불려오는 메서드가 다르다)
        // 쿼리 1번에 다 끝남 (멤버와 delivery가 order 쿼리에 포함되어서 나옴)
        // 실무에서 진짜 너무너무너무너무 중요함
    }

    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        // 엔티티 -> Dto로 변환이 아니라 그냥 Dto로 받아와서 반환
        return orderRepository.findOrderDtos();
    }

    // V3는 원하는 것만 jetch join으로 가져올 수 있음
    // V4는 재사용성 X, 저 Dto를 쓸 때만 사용 가능 (V3는 재사용도 가능하긴 함)
    // 어느 것이 좋다기 보다는 서로 장단점이 있음


    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) { // Dto에서 파라미터로 엔티티를 받아오는건 괜찮다.
            orderId = order.getId();
            name = order.getMember().getName(); // 여기랑
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // 여기에서 쿼리가 나감
            // order 쿼리 나가고, 각각의 member 쿼리 나가고, delivery 쿼리 나가고
        }
    }
}
