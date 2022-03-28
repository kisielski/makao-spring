package pl.makao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@ToString
@Entity
@Table(name="games")
public class Game {

    public enum GameState {OPEN, PLAYING, FINISHED}

    @Id
    private int id;

    private int numPlayers = 0;
    private GameState state = GameState.OPEN;
    @OneToOne
    private Deck deck;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "game")
    private List<Hand> players = new ArrayList<>();

    private int turnPlayerId;
    private boolean cardRequested = false;
    @OneToOne
    private Card topCard;
    private int requestedVal;
    private int changedSuit;
    private int currentPenalty;


    public final static int MAX_PLAYERS = 4;

    public Game(int id) {
        this.id = id;
        //this.deck = new Deck(this, id);
    }

    public boolean join(Hand playerHand) {
        if(state.equals(GameState.OPEN)) {
            players.add(playerHand);
            numPlayers++;
            if(numPlayers == MAX_PLAYERS) {
                // GAME STARTS
                state = GameState.PLAYING;
                topCard = getTopCard();
            }
            return true;
        }
        return false;
    }

    public boolean hasTurn(int playerId) {
        if(turnPlayerId == playerId) return true;
        return false;
    }

    public boolean putCard(int playerId, Card card) {
        int cardSuit = card.getSuit(), cardVal = card.getValue();
        int topCardSuit = topCard.getSuit(), topCardVal = topCard.getValue();
        if(cardSuit == topCardSuit || cardVal == topCardVal || cardVal == requestedVal || cardSuit == changedSuit || cardVal == 12) {
            if(cardVal == 2 || cardVal == 3) {
                currentPenalty += cardVal;
                topCard = card;
                return true;
            }
            else if(cardVal == 4) {
                ;
            }
        }
        return false;
    }

    public Card drawTopCard() {
        return deck.getTopCard();
    }

    public Hand getHand(int playerId) {
        return players.stream().filter(e -> e.getId() == playerId).findFirst().orElseGet(() -> null);
    }

    public Map<Integer, Integer> getOtherPlayersHands(int playerId) {
        Map<Integer, Integer> otherHands = new HashMap<>();
        players.forEach(hand ->  {
            if(hand.getId() != playerId) {
                otherHands.put(hand.getId(), hand.getCards().size());
            }
        });
        return otherHands;
    }

    public Map<Integer, Boolean> getOtherPlayersMakaoState(int playerId) {
        Map<Integer, Boolean> otherMakao = new HashMap<>();
        players.forEach(hand -> {
            otherMakao.put(hand.getId(), hand.isSaidMakao());
        });
        return otherMakao;
    }
}
