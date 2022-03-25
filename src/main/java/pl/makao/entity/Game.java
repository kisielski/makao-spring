package pl.makao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
public class Game {

    public enum GameState {OPEN, PLAYING, FINISHED}

    private int id;
    private GameState state;
    private Deck deck;
    private Map<Integer, Hand> players;

    public Game(int id) {
        this.id = id;
        this.state = GameState.OPEN;
        this.deck = new Deck(id);
    }

    public boolean join(int playerId) {
        if(state.equals(GameState.OPEN)) {
            players.put(playerId, new Hand(playerId));
            return true;
        }
        return false;
    }

    public Hand getHand(int playerId) {
        if(players.containsKey(playerId)) {
            return players.get(playerId);
        }
        return null;
    }

    public List<Integer> getOtherPlayersHands(int playerId) {
        List<Integer> otherHands = new ArrayList<>();
        players.forEach((ownerId, hand) ->  {
            if(!ownerId.equals(playerId)) {
                otherHands.add(hand.getCards().size());
            }
        });
        return otherHands;
    }
}
