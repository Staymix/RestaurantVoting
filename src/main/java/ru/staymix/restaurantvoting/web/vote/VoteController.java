package ru.staymix.restaurantvoting.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.service.VoteService;
import ru.staymix.restaurantvoting.to.VoteTo;
import ru.staymix.restaurantvoting.util.VotesUtil;
import ru.staymix.restaurantvoting.web.AuthUser;

import java.net.URI;
import java.util.List;

import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {
    static final String REST_URL = "api/votes";

    private VoteService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@RequestBody Vote vote) {
        int userId = AuthUser.authId();
        log.info("create {} by user id={}", vote, userId);
        checkNew(vote);
        Vote created = service.create(vote, userId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping()
    public List<VoteTo> getAll() {
        int userId = AuthUser.authId();
        log.info("getAll for user id={}", userId);
        return VotesUtil.getTos(service.getAll(userId));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Vote vote, @PathVariable int id) {
        int userId = AuthUser.authId();
        log.info("update {} for user id={}", vote, userId);
        assureIdConsistent(vote, id);
        service.update(vote, userId);
    }
}
