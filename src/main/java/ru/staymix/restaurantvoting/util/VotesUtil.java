package ru.staymix.restaurantvoting.util;

import lombok.experimental.UtilityClass;
import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.to.VoteTo;

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

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getRestaurant().id());
    }
}
