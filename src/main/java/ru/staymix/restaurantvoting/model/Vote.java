package ru.staymix.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "vote", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "vote_date"}, name = "vote_unique_user_date_idx")})
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity {

    @Column(name = "vote_date")
    @NotNull
    private LocalDate voteDate;

    @Column(name = "vote_time")
    @NotNull
    private LocalTime voteTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    @NotNull
    private Restaurant restaurant;

    public Vote(Integer id, @NotNull LocalDate voteDate, @NotNull LocalTime voteTime, @NotNull User user, @NotNull Restaurant restaurant) {
        super(id);
        this.voteDate = voteDate;
        this.voteTime = voteTime;
        this.user = user;
        this.restaurant = restaurant;
    }
}
