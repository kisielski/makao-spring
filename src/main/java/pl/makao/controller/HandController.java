package pl.makao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pl.makao.dto.CreateHandRequest;
import pl.makao.dto.GetHandResponse;
import pl.makao.entity.Hand;
import pl.makao.service.HandService;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("api/hands")
public class HandController {

    private HandService service;

    @Autowired
    public HandController(HandService service) { this.service = service; }

    @GetMapping("{owner}")
    public ResponseEntity<GetHandResponse> getHand(@PathVariable("owner") int owner) {
        Optional<Hand> hand = service.findByOwner(owner);
        return hand.map(value -> ResponseEntity.ok(GetHandResponse.entityToDtoMapper().apply(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> createHand(@RequestBody CreateHandRequest request, UriComponentsBuilder builder) {
        Hand hand = CreateHandRequest
                .dtoToEntityMapper()
                .apply(request);
        hand = service.create(hand);
        return ResponseEntity.created(builder.pathSegment("api", "hands", "{owner}").buildAndExpand(hand.getOwner()).toUri()).build();
    }

    @DeleteMapping("{owner}")
    public ResponseEntity<Void> deleteHand(@PathVariable("owner") int owner) {
        Optional<Hand> hand = service.findByOwner(owner);
        if(hand.isPresent()) {
            service.delete(hand.get());
            return ResponseEntity.accepted().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{owner}")
    public ResponseEntity<Void> updateHand();

    @PutMapping("{model}")
    public ResponseEntity<Void> updateEngine(@RequestBody UpdateEngineRequest request, @PathVariable("model") String model) {
        Optional<Engine> engine = engineService.find(model);
        if (engine.isPresent()) {
            UpdateEngineRequest.dtoToEntityUpdater().apply(engine.get(), request);
            engineService.update(engine.get());
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
