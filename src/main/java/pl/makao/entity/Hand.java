package pl.makao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import pl.makao.entity.Card;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@SuperBuilder
@Table(name="hands")
public class Hand {

    private List<Card> cards;

    @Id
    private int owner;

    public Hand(int owner) {
        this.owner = owner;
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public boolean hasCard(Card card) {
        Optional<Card> cardFound = cards.stream().findAny();
        if(cardFound.isPresent()) {
            cards.remove(cardFound);
            return true;
        }
        else{
            return false;
        }
    }
}
