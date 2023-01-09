package cc.ddrpa.dorian.domain.ship;

import cc.ddrpa.dorian.domain.ship.enums.ShipStatus;
import cc.ddrpa.dorian.domain.ship.enums.ShipType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ShipRepositoryTest {
    @Autowired
    private ShipRepository shipRepository;

    @Test
    void repository_should_successfully_save_ship() {
        var ship = new Ship()
                .setName("Kun")
                .setType(ShipType.RESEARCH_VESSEL)
                .setOwner("Jun Lee")
                .setStatus(ShipStatus.ANCHORED)
                .setLastReportTime(LocalDateTime.now())
                .setLastKnownLocation("Fortune-9");
        var savedShip = shipRepository.save(ship);
        assertNotNull(savedShip.getId());
        assertEquals(savedShip.getId(), ship.getId());
        // assert other properties are equal
    }
}