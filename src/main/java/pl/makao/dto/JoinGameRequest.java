package pl.makao.dto;

import lombok.*;
import pl.makao.entity.Game;
import pl.makao.entity.Hand;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JoinGameRequest {

    private int handId;

    public static BiFunction<Game, JoinGameRequest, Game> dtoToEntityUpdater(Hand hand) {
        return (game, request) -> {
            hand.joinGame(game);
            game.join(hand);
            return game;
        };
    }
}
