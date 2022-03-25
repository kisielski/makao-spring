package pl.makao.entity;

import java.util.Random;

public class Deck extends Hand{

    private Random rand = new Random();

    public Deck(int owner) {
        super(owner);
    }

    public Card getTopCard() {
        if(getCards().size() > 0) {
            return getCards().get(rand.nextInt(getCards().size()));
        }
        // TODO: else handle exception
        return null;
    }
}
