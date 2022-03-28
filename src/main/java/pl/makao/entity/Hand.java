package pl.makao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.makao.entity.Card;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
@Entity
@Table(name="hands")
public class Hand {

    @ElementCollection
    private List<Card> cards = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="game")
    private Game game;

    private boolean saidMakao = false;

    private int stopRounds = 0;

    @Id
    private int id;

    public Hand(int id) {
        this.id = id;
    }

    public void joinGame(Game game) {
        this.game = game;
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
