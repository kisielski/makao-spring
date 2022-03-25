package pl.makao.entity;

import java.util.List;
import java.util.Optional;

public class GamesLister {

    private List<Game> games;

    public Game createNewGame() {
        Game game = new Game(games.size());
        games.add(game);
        return game;
    }

    public void deleteGame(int id) {
        Optional<Game> foundGame = games.stream().filter(game -> game.getId() == id).findFirst();
        if(foundGame.isPresent()) {
            games.remove(foundGame);
        }
    }
}
