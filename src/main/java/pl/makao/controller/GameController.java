package pl.makao.controller;

import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.makao.dto.*;
import pl.makao.entity.Card;
import pl.makao.entity.Game;
import pl.makao.entity.Hand;
import pl.makao.service.GameService;
import pl.makao.service.HandService;

import java.util.Optional;

@RestController
@RequestMapping("game")
public class GameController {

    private GameService service;

    private HandService handService;

    @Autowired
    public GameController(GameService service, HandService handService) { this.service = service; this.handService = handService; }

    @GetMapping
    public ResponseEntity<GetGamesResponse> getGames() {
        return ResponseEntity.ok(GetGamesResponse.entityToDtoMapper().apply(service.findAll()));
    }

    @PostMapping
    public ResponseEntity<Void> createGame(UriComponentsBuilder builder) {
        int gameId = service.findFreeId();
        Game game = new Game(gameId);
        game = service.create(game);
        return ResponseEntity.created(builder.pathSegment("game", "{gameId}").buildAndExpand(game.getId()).toUri()).build();
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

    /*
    @PostMapping("{gameId}/join")
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
    */

    @PostMapping("{gameId}")
    public ResponseEntity<Void> joinGame(@RequestBody JoinGameRequest request, @PathVariable("gameId") int gameId) {
        Optional<Game> game = service.findById(gameId);
        if(game.isPresent()) {
            Game foundGame = game.get();
            if(foundGame.getPlayers().stream().noneMatch(e -> e.getId() == request.getHandId())) {
                Hand foundHand = handService.findById(request.getHandId()).orElseThrow();
                JoinGameRequest.dtoToEntityUpdater(foundHand).apply(foundGame, request);
                service.update(foundGame);
                handService.update(foundHand);
                return ResponseEntity.accepted().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("putCard")
    public ResponseEntity putCard(@RequestBody Card card, @RequestBody int gameId, @RequestBody int playerId) {
        Optional<Game> game = service.findById(gameId);
        game.map(foundGame -> {
            if (game.get().hasTurn(playerId)) {
                game.get().putCard(playerId, card);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(420).build();
        });
        return ResponseEntity.status(420).build();
    }
}
