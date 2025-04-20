package jpabook.japshop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@DiscriminatorValue("B")
@Entity
public class Book extends Item {

    private String author;
    private String isbn;
}
