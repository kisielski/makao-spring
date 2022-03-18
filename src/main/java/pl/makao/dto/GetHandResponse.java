package pl.makao.dto;

import lombok.*;
import pl.makao.entity.Card;
import pl.makao.entity.Hand;

import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetHandResponse {

    private List<Card> cards;

    private int owner;

    public static Function<Hand, GetHandResponse> entityToDtoMapper() {
        return hand->GetHandResponse.builder()
                .cards(hand.getCards())
                .owner(hand.getOwner())
                .build();
    }
}
