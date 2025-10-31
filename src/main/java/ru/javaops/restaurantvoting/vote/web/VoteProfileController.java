package ru.javaops.restaurantvoting.vote.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.restaurantvoting.app.AuthUser;
import ru.javaops.restaurantvoting.vote.model.Vote;
import ru.javaops.restaurantvoting.vote.service.VoteService;
import ru.javaops.restaurantvoting.vote.to.VoteTo;

import java.net.URI;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(value = VoteProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteProfileController {

    static final String REST_URL = "/api/profile/votes";

    private final VoteService service;

    @GetMapping("/today")
    public VoteTo getTodayVote(@AuthenticationPrincipal AuthUser authUser) {
        return service.getTodayVote(authUser.id());
    }

    @PostMapping
    public ResponseEntity<VoteTo> create(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        log.info("user {} voting for restaurant {}", authUser.id(), restaurantId);
        Vote vote = service.create(authUser.getUser(), restaurantId);
        VoteTo voteTo = new VoteTo(vote.getId(), vote.getRestaurant().getId(), vote.getRestaurant().getName(), vote.getTime());

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL + "/today").build().toUri();
        return ResponseEntity.created(uri).body(voteTo);
    }

    @PutMapping
    @ResponseStatus(NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser, @RequestParam int restaurantId) {
        service.update(authUser.id(), restaurantId);
    }

    @DeleteMapping
    public void deleteTodayVote(@AuthenticationPrincipal AuthUser authUser) {
        service.deleteTodayVote(authUser.id());
    }
}