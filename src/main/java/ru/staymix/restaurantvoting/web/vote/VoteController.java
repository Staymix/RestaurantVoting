package ru.staymix.restaurantvoting.web.vote;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.service.RestaurantService;
import ru.staymix.restaurantvoting.service.VoteService;
import ru.staymix.restaurantvoting.to.VoteTo;
import ru.staymix.restaurantvoting.util.VotesUtil;
import ru.staymix.restaurantvoting.web.AuthUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.staymix.restaurantvoting.util.VotesUtil.getTos;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteController {
    static final String REST_URL = "/api/user/votes";
    protected VoteService voteService;
    protected RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<VoteTo> create(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("create vote restaurant id={} by user id={}", restaurantId, authUser.id());
        Vote create = voteService.create(new Vote(null, LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
                authUser.getUser(), restaurantService.get(restaurantId)), authUser.id());
        return new ResponseEntity<>(VotesUtil.createTo(create), HttpStatus.CREATED);
    }

    @GetMapping()
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll by user id={}", authUser.id());
        return getTos(voteService.getAll(authUser.id()));
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update vote restaurant id={} by user id={}", restaurantId, authUser.id());
        Vote updated = voteService.getTodayByUser(authUser.id());
        updated.setVoteTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
        voteService.update(updated, authUser.id(), restaurantId);
    }
}
