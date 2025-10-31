package ru.javaops.restaurantvoting.vote.repository;

import org.springframework.data.jpa.repository.Query;
import ru.javaops.restaurantvoting.common.BaseRepository;
import ru.javaops.restaurantvoting.vote.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id = :userId AND v.date = :date")
    Optional<Vote> findByUserIdAndDate(Integer userId, LocalDate date);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.restaurant.id = :restaurantId AND v.date = :date")
    int countByRestaurantAndDate(Integer restaurantId, LocalDate date);

    @Query("SELECT v.restaurant.id, COUNT(v) FROM Vote v WHERE v.date = :date GROUP BY v.restaurant.id")
    List<Object[]> findVotingResultsByDate(LocalDate date);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id = :userId ORDER BY v.date DESC, v.time DESC")
    List<Vote> findUserVotingHistory(Integer userId);

}