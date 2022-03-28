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

    private int turnPlayerId;

    private boolean cardRequested = false;

    private Card topCard;

    private int requestedVal = -1;

    private int changedSuit = -1;

    private int currentPenalty = 0;

    public static Function<Hand, GetBoardStateResponse> entityToDtoMapper() {
        return hand -> {
            Game game = hand.getGame();
            return GetBoardStateResponse.builder()
                    .playerHand(hand)
                    .otherPlayersHands(game.getOtherPlayersHands(hand.getId()))
                    .otherPlayersMakaoState(game.getOtherPlayersMakaoState(hand.getId()))
                    .cardRequested(game.isCardRequested())
                    .turnPlayerId(game.getTurnPlayerId())
                    .topCard(game.getTopCard())
                    .requestedVal(game.getRequestedVal())
                    .changedSuit(game.getChangedSuit())
                    .currentPenalty(game.getCurrentPenalty())
                    .build();
        };
    }
}
