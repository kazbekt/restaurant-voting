package ru.javaops.restaurantvoting.vote.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.restaurantvoting.vote.service.VoteService;
import ru.javaops.restaurantvoting.vote.to.VoteTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = VoteAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class VoteAdminController {

    static final String REST_URL = "/api/admin/votes";

    private final VoteService service;

    @GetMapping("/results/today")
    public Map<Integer, Long> getTodayResults() {
        return service.getTodayVotingResults();
    }

    @GetMapping("/results")
    public Map<Integer, Long> getResultsByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getVotingResultsByDate(date);
    }

    @GetMapping("/user/{userId}")
    public List<VoteTo> getUserVotes(@PathVariable int userId) {
        return service.findUserVotes(userId);
    }
}