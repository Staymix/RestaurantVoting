package ru.staymix.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.to.VoteTo;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class VotesUtil {

    public static final LocalTime TIME_LIMIT = LocalTime.of(11, 0);

    public static List<VoteTo> getTos(Collection<Vote> votes) {
        return votes.stream()
                .map(VotesUtil::createTo)
                .collect(Collectors.toList());
    }

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getRestaurant().getId(), vote.getVoteDate());
    }

    public static boolean isBeforeTimeLimit(Vote vote) {
        return vote.getVoteTime().isBefore(TIME_LIMIT);
    }
}
