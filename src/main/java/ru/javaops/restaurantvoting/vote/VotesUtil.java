package ru.javaops.restaurantvoting.vote;

import lombok.experimental.UtilityClass;
import ru.javaops.restaurantvoting.vote.model.Vote;
import ru.javaops.restaurantvoting.vote.to.VoteTo;

import java.util.Collection;
import java.util.List;

@UtilityClass
public class VotesUtil {

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRestaurant().getId(), vote.getDate(), vote.getTime());
    }

    public static List<VoteTo> getVoteTos(Collection<Vote> Vote) {
        return Vote.stream().map(VotesUtil::createTo).toList();
    }
}