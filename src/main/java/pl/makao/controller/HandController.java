package pl.makao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.makao.dto.CreateHandRequest;
import pl.makao.dto.GetBoardStateResponse;
import pl.makao.dto.GetHandResponse;
import pl.makao.entity.Card;
import pl.makao.entity.Game;
import pl.makao.entity.Hand;
import pl.makao.service.GameService;
import pl.makao.service.HandService;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class HandController {

    private HandService service;

    private GameService gameService;

    @Autowired
    public HandController(HandService service, GameService gameService) { this.service = service; this.gameService = gameService; }

    @GetMapping("{playerId}")
    public ResponseEntity<GetBoardStateResponse> getBoard(@PathVariable("playerId") int playerId) {
        Optional<Hand> hand = service.findById(playerId);
        return hand.map(value -> ResponseEntity.ok(GetBoardStateResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity createHand(UriComponentsBuilder builder) {
        int playerId = service.findFreeId();
        Hand hand = new Hand(playerId);
        hand = service.create(hand);
        return ResponseEntity.created(builder.pathSegment("api", "{playerId}").buildAndExpand(hand.getId()).toUri()).build();
    }

    @PutMapping("{playerId}/putCard")
    public ResponseEntity putCard(@RequestBody Card card, @PathVariable("playerId") int playerId) {
        Optional<Hand> hand = service.findById(playerId);
        return hand.map(value -> {
            Game game = value.getGame();
            if(game.hasTurn(playerId)) {
                game.putCard(playerId, card);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(420).build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
