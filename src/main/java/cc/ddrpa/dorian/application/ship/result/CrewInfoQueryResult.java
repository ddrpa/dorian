package cc.ddrpa.dorian.application.ship.result;

import cc.ddrpa.dorian.domain.ship.Crew;

public record CrewInfoQueryResult(
        String name
) {
    public CrewInfoQueryResult(Crew crew) {
        this(crew.getName());
    }
}
