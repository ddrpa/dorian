package cc.ddrpa.dorian.domain.ship;

import cc.ddrpa.dorian.infrastructure.exception.OperationNotAllowedException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
@Validated
public class ShipService {
    private static final Logger logger = LoggerFactory.getLogger(ShipService.class);
    private final ShipRepository shipRepository;
    private final CrewRepository crewRepository;

    public Ship register(Ship ship) {
        Ship newShip = new Ship(ship.getName(), ship.getType(), ship.getOwner());
        shipRepository.save(newShip);
        return newShip;
    }

    public List<Crew> registerCrews(Integer shipId, List<Crew> crews) {
        if (Objects.isNull(crews) || crews.size() < 1) {
            return List.of();
        }
        if (shipRepository.existsById(shipId)) {
            List<Crew> newCrews = crews
                    .stream()
                    .map(crew -> new Crew(crew.getName(), shipId))
                    .toList();
            crewRepository.saveAll(newCrews);
            return newCrews;
        } else {
            logger.warn("attempt register crews for non-exist ship {}", shipId);
            throw new OperationNotAllowedException("can not register crews for non-exist ship %s".formatted(shipId));
        }
    }

    public void unregister(Integer shipId) {
        if (shipId.equals(11)) {
            logger.warn("shipId {} is not allowed to be unregistered", shipId);
            throw new OperationNotAllowedException("shipId %s is not allowed to be unregistered".formatted(shipId));
        }
        if (shipRepository.existsById(shipId)) {
            crewRepository.deleteAllByShipId(shipId);
            shipRepository.deleteById(shipId);
        } else {
            logger.warn("attempt unregister non-exist ship {}", shipId);
            throw new OperationNotAllowedException("can not unregister non-exist ship %s".formatted(shipId));
        }
    }
}