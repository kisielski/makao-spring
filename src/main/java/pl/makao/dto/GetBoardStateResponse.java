package pl.makao.dto;

import lombok.*;
import pl.makao.entity.Card;
import pl.makao.entity.Game;
import pl.makao.entity.Hand;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardStateResponse {

    private Hand playerHand;

    private Map<Integer, Integer> otherPlayersHands = new HashMap<>();

    private Map<Integer, Boolean> otherPlayersMakaoState = new HashMap<>();

    private boolean cardRequested;

    private Card request;

    private int currentPenalty;

    private Card topCard;

    public static Function<Hand, GetBoardStateResponse> entityToDtoMapper() {
        return hand -> {
            Game game = hand.getGame();
            return GetBoardStateResponse.builder()
                    .playerHand(hand)
                    .otherPlayersHands(game.getOtherPlayersHands(hand.getOwner()))
                    .otherPlayersMakaoState(game.getOtherPlayersMakaoState(hand.getOwner()))
                    .cardRequested(game.isCardRequested())
                    .request(game.getRequest())
                    .currentPenalty(game.getCurrentPenalty())
                    .topCard(game.getTopCard())
                    .build();
        };
    }
}
