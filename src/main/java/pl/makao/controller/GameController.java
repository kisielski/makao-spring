package pl.makao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.makao.dto.CreateGameRequest;
import pl.makao.dto.CreateHandRequest;
import pl.makao.dto.GetBoardStateResponse;
import pl.makao.dto.GetHandResponse;
import pl.makao.entity.Game;
import pl.makao.entity.Hand;
import pl.makao.service.GameService;

import java.util.Optional;

@RestController
@RequestMapping("games")
public class GameController {

    private GameService service;

    @Autowired
    public GameController(GameService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<Void> createGame(@RequestBody CreateGameRequest request, UriComponentsBuilder builder) {
        Game game = CreateGameRequest
                .dtoToEntityMapper()
                .apply(request);
        game = service.create(game);
        return ResponseEntity.created(builder.pathSegment("games", "{gameId}").buildAndExpand(game.getId()).toUri()).build();
    }

    @DeleteMapping("{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable("gameId") int gameId) {
        Optional<Game> game = service.findById(gameId);
        if(game.isPresent()) {
            service.delete(game.get());
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{gameId}/join")
    public ResponseEntity<Void> joinGame(@RequestBody int playerId, @PathVariable("gameId") int gameId) {
        Optional<Game> game = service.findById(gameId);
        if(game.isPresent()) {
            if(game.get().join(playerId)) {
                service.update(game.get());
                return ResponseEntity.accepted().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
