package hello.itemservice.domain.item;

import lombok.Data;

@Data // 실무에서는 @Getter @Setter 쓰고 Data는 잘 안씀
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {

    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
