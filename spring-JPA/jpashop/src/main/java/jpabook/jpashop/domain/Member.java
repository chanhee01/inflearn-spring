package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    // Address 클래스의 @Embeddable과 이것중 하나만 적어도 되긴하는데 둘 다 적는 편
    private Address address;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    // order table에 있는 member 필드에 의해 매핑되었다는 뜻
    // 연관관계의 주인은 FK가 가까운 곳으로 하면 됨 -> order에 있는 foreign key가 주인이다.
    private List<Order> orders = new ArrayList<>();
}

// 일대 다 관계에서는 FK를 다수쪽에 넣기(Member와 Order가 있으면 Order에 FK)
// 일대일 관계에서는 FK를 access 많이 하는 곳에 넣기
// 다대 다는 실무에서 사용하면 안됨
