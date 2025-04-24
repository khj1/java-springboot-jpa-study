package jpabook.japshop.domain.item;

import jakarta.persistence.*;
import jpabook.japshop.domain.categoryitem.CategoryItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dType")
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<CategoryItem> categoryItems = new ArrayList<>();

    public void removeStock(int count) {
        if (stockQuantity < count) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        stockQuantity -= count;
    }

    public void addStock(int count) {
        stockQuantity += count;
    }
}
