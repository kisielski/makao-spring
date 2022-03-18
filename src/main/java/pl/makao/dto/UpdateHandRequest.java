package pl.makao.dto;

import lombok.*;
import pl.makao.entity.Card;

import java.util.function.BiFunction;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateHandRequest {

    private Card drawnCard = null;

    private Card putCard = null;
}
