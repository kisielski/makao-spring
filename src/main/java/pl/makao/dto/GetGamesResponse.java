package pl.makao.dto;

import lombok.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetGamesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Game {

        private int gameId;

        private int numPlayers;

        private String state;
    }

    @Singular
    private List<GetGamesResponse.Game> games;

    public static Function<List<pl.makao.entity.Game>, GetGamesResponse> entityToDtoMapper() {
        return games -> {
            GetGamesResponse.GetGamesResponseBuilder response = GetGamesResponse.builder();
            games.stream()
                    .map(game -> GetGamesResponse.Game.builder()
                        .gameId(game.getId())
                        .numPlayers(game.getNumPlayers())
                        .state(game.getState().name())
                        .build())
                    .forEach(response::game);
            return response.build();
        };
    }
}
