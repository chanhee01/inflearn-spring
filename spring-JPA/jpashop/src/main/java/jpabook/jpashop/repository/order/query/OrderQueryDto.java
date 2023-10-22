package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderQueryDto {

    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

}
