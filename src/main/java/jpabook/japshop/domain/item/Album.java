package jpabook.japshop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@DiscriminatorValue("A")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Album extends Item {

    private String artist;
    private String etc;
}
