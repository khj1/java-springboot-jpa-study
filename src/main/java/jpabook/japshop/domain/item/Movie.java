package jpabook.japshop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@DiscriminatorValue("M")
@Entity
public class Movie extends Item {

    private String director;
    private String actor;
}
