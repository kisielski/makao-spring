package pl.makao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@SuperBuilder
@Entity
public class Card {

    private int value;

    private int suit;

    @Id
    private int id;

    public Card(int value, int suit) {
        this.value = value;
        this.suit = suit;
        this.id = suit * 14 + value;
    }
}
