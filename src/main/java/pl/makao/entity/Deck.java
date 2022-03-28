package pl.makao.entity;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class Deck extends Hand{

    private Random rand = new Random();

    public Deck(int gameId) {
        super(gameId);
    }

    public Card getTopCard() {
        if(getCards().size() > 0) {
            return getCards().get(rand.nextInt(getCards().size()));
        }
        // TODO: else handle exception
        return null;
    }
}
