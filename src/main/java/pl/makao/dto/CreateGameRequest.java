package pl.makao.dto;

import lombok.*;
import pl.makao.entity.Card;
import pl.makao.entity.Game;
import pl.makao.entity.Hand;

import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateGameRequest {

    private int gameId;

    public static Function<CreateGameRequest, Game> dtoToEntityMapper() {
        return request -> Game.builder()
                .id(request.gameId)
                .build();
    }
}
