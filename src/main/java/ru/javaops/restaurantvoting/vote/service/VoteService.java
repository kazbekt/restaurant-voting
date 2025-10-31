package ru.javaops.restaurantvoting.vote.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurantvoting.app.util.Util;
import ru.javaops.restaurantvoting.common.error.DataConflictException;
import ru.javaops.restaurantvoting.common.error.NotFoundException;
import ru.javaops.restaurantvoting.restaurant.model.Restaurant;
import ru.javaops.restaurantvoting.restaurant.repository.RestaurantRepository;
import ru.javaops.restaurantvoting.user.model.User;
import ru.javaops.restaurantvoting.vote.model.Vote;
import ru.javaops.restaurantvoting.vote.repository.VoteRepository;
import ru.javaops.restaurantvoting.vote.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javaops.restaurantvoting.app.util.Util.UPDATE_AFTER_DEADLINE_MSG;
import static ru.javaops.restaurantvoting.vote.VotesUtil.createTo;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class VoteService {
    public static final String VOTED_TODAY = "You have already voted today";
    public static final String NO_VOTE_FOR_TODAY = "No vote for today";

    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;

    public Vote create(User user, int restaurantId) {
        LocalDate today = LocalDate.now();

        if (repository.findByUserIdAndDate(user.getId(), today).isPresent()) {
            throw new DataConflictException(VOTED_TODAY);
        }

        Restaurant restaurant = restaurantRepository.getExisted(restaurantId);

        Vote vote = new Vote();
        vote.setUser(user);
        vote.setRestaurant(restaurant);
        vote.setDate(today);
        vote.setTime(LocalTime.now());

        repository.save(vote);

        return repository.findByUserIdAndDate(user.getId(), today)
                .orElseThrow(() -> new IllegalStateException("Vote not found after save"));
    }

    public void update(int userId, int restaurantId) {

        Vote existing = findTodayUserVote(userId);

        if (Util.isAfterDeadline(LocalTime.now())) {
            throw new DataConflictException(UPDATE_AFTER_DEADLINE_MSG);
        }
        existing.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        existing.setTime(LocalTime.now());

        repository.save(existing);
    }

    public void deleteTodayVote(int userId) {
        Vote existing = findTodayUserVote(userId);
        int id = existing.getId();
        repository.deleteExisted(id);
    }

    public VoteTo getTodayVote(int userId) {
        Vote vote = findTodayUserVote(userId);
        return createTo(vote);
    }

    public Map<Integer, Long> getVotingResultsByDate(LocalDate date) {
        List<Object[]> results = repository.findVotingResultsByDate(date);
        return results.stream()
                .collect(Collectors.toMap(
                        arr -> (Integer) arr[0],
                        arr -> (Long) arr[1]
                ));
    }

    public Map<Integer, Long> getTodayVotingResults() {
        return getVotingResultsByDate(LocalDate.now());
    }

    public List<VoteTo> findUserVotes(int userId) {
        return repository.findUserVotingHistory(userId).stream()
                .map(vote -> new VoteTo(vote.getId(), vote.getRestaurant().getId(), vote.getDate(), vote.getTime()))
                .collect(Collectors.toList());
    }

    private Vote  findTodayUserVote(int userId) {
        LocalDate today = LocalDate.now();
        return repository.findByUserIdAndDate(userId, today)
                .orElseThrow(() -> new NotFoundException(NO_VOTE_FOR_TODAY));
    }
}