package ru.staymix.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class VotesUtil {

    public static List<VoteTo> getTos(Collection<Vote> votes) {
        return votes.stream()
                .map(VotesUtil::createTo)
                .collect(Collectors.toList());
    }

    private static VoteTo createTo(Vote vote) {
        LocalDate voteDate = vote.getVoteDate();
        LocalTime voteTime = vote.getVoteTime();
        return new VoteTo(vote.id(), vote.getRestaurant().id(), vote.getUser().id(), voteDate, voteTime, (voteDate.isAfter(LocalDate.now()) && voteTime.isAfter(LocalTime.of(11, 0))));
    }
}
