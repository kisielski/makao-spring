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
import pl.makao.service.HandService;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class HandController {

    private HandService service;

    @Autowired
    public HandController(HandService service) { this.service = service; }

    @GetMapping("{playerId}")
    public ResponseEntity<GetBoardStateResponse> getBoard(@PathVariable("playerId") int playerId) {
        Optional<Hand> hand = service.findByOwner(playerId);
        return hand.map(value -> ResponseEntity.ok(GetBoardStateResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("{playerId}/putCard")
    public ResponseEntity putCard(@RequestBody Card card, @PathVariable("playerId") int playerId) {
        Optional<Hand> hand = service.findByOwner(playerId);
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
