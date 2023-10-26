package ru.staymix.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class VoteTo extends BaseTo {

    private final int restaurantId;
    private final LocalDate voteDate;

    public VoteTo(Integer id, int restaurantId, LocalDate voteDate) {
        super(id);
        this.restaurantId = restaurantId;
        this.voteDate = voteDate;
    }
}
