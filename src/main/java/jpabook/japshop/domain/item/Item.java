package jpabook.japshop.domain.item;

import jakarta.persistence.*;
import jpabook.japshop.domain.categoryitem.CategoryItem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dType")
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
}
