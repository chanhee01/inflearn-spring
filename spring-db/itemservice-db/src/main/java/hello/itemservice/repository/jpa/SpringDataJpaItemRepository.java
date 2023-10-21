package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringDataJpaItemRepository extends JpaRepository<Item, Long>  {
    // save, update, findAll등은 스프링 데이터 JPA가 기본으로 제공
    // 이외에 우리가 사용할 기능만 짜면 되는데 이름으로 스프링 데이터가 자동으로 기능을 구현함

    List<Item> findByItemNameLike(String itemName);
    List<Item> findByPriceLessThanEqual(Integer price);

    // 쿼리 메서드 (아래 메서드와 같은 기능 수행)
    List<Item> findByItemNameLikeAndPriceLessThanEqual(String itemName, Integer price);
    // 너무 길어서 좋지 않은 방법이다

    // 쿼리 직접 실행
    @Query("select i from Item i where i.itemName Like :itemName and i.price <= :price")
    List<Item> findItems(@Param("itemName") String itemName, @Param("price") Integer price);
}
