package ru.staymix.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString
public class VoteTo extends BaseTo {

    private final int restaurantId;

    private final int userId;

    private final LocalDate voteDate;

    private final LocalTime voteTime;

    private final boolean isModifiable;

    public VoteTo(Integer id, int restaurantId, int userId, LocalDate voteDate, LocalTime voteTime, boolean isModifiable) {
        super(id);
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.voteDate = voteDate;
        this.voteTime = voteTime;
        this.isModifiable = isModifiable;
    }
}
