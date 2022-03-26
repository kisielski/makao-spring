package pl.makao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
public class Game {

    public enum GameState {OPEN, PLAYING, FINISHED}

    private int id;
    private GameState state = GameState.OPEN;
    private Deck deck;
    private Map<Integer, Hand> players;
    private boolean cardRequested = false;
    private Card request;
    private int currentPenalty = 0;

    public Game(int id) {
        this.id = id;
        this.deck = new Deck(this, id);
    }

    public boolean join(int playerId) {
        if(state.equals(GameState.OPEN)) {
            players.put(playerId, new Hand(this, playerId));
            return true;
        }
        return false;
    }

    public Card getTopCard() {
        return deck.getTopCard();
    }

    public Hand getHand(int playerId) {
        if(players.containsKey(playerId)) {
            return players.get(playerId);
        }
        return null;
    }

    public Map<Integer, Integer> getOtherPlayersHands(int playerId) {
        Map<Integer, Integer> otherHands = new HashMap<>();
        players.forEach((ownerId, hand) ->  {
            if(!ownerId.equals(playerId)) {
                otherHands.put(hand.getOwner(), hand.getCards().size());
            }
        });
        return otherHands;
    }

    public Map<Integer, Boolean> getOtherPlayersMakaoState(int playerId) {
        Map<Integer, Boolean> otherMakao = new HashMap<>();
        players.forEach((ownerId, hand) ->  {
            if(!ownerId.equals(playerId)) {
                otherMakao.put(hand.getOwner(), hand.isSaidMakao());
            }
        });
        return otherMakao;
    }
}
