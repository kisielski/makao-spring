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
public class CreateHandRequest {

    private List<Card> cards;

    private int id;

    public static Function<CreateHandRequest, Hand> dtoToEntityMapper() {
        return request->Hand.builder()
                .cards(request.cards)
                .id(request.id)
                .build();
    }
}
