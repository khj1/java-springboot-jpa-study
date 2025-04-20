package jpabook.japshop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@DiscriminatorValue("A")
@Entity
public class Album extends Item {

    private String artist;
    private String etc;
}
