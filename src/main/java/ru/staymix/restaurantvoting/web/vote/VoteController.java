package ru.staymix.restaurantvoting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.service.RestaurantService;
import ru.staymix.restaurantvoting.service.VoteService;
import ru.staymix.restaurantvoting.to.VoteTo;
import ru.staymix.restaurantvoting.util.VotesUtil;
import ru.staymix.restaurantvoting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {
    static final String REST_URL = "api/votes";

    protected VoteService voteService;
    protected RestaurantService restaurantService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@RequestBody VoteTo voteTo, @AuthenticationPrincipal AuthUser authUser) {
        log.info("create {} by user id={}", voteTo, authUser.id());
        checkNew(voteTo);
        Vote create = new Vote(null, LocalDate.now(), LocalTime.now(), authUser.getUser(), restaurantService.get(voteTo.getRestaurantId()));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(create.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(create);
    }

    @GetMapping("/{id}")
    public VoteTo get(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("get vote id={} for user id={}", id, authUser.id());
        return VotesUtil.createTo(voteService.get(id, authUser.id()));
    }

    @GetMapping()
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAll for user id={}", authUser.id());
        return VotesUtil.getTos(voteService.getAll(authUser.id()));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody VoteTo voteTo, @PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {} for user id={}", voteTo, authUser.id());
        assureIdConsistent(voteTo, id);
        Vote updated = voteService.get(id, authUser.id());
        updated.setRestaurant(restaurantService.get(voteTo.getRestaurantId()));
        voteService.update(updated, authUser.id());
    }
}
