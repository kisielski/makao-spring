package pl.makao.dto;

import lombok.*;
import pl.makao.entity.Card;
import pl.makao.entity.Game;
import pl.makao.entity.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardState {

    private Hand playerHand;

    private List<Integer> otherPlayersHands = new ArrayList<>();

    public static Function<Game, GetBoardState> entityToDtoMapper() {
        return game -> GetBoardState.builder()
                .playerHand(game.getHand())
                .build();
    }
}
