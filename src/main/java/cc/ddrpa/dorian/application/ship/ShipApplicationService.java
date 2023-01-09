package cc.ddrpa.dorian.application.ship;

import cc.ddrpa.dorian.application.ship.command.RegisterShipCommand;
import cc.ddrpa.dorian.application.ship.result.CrewInfoQueryResult;
import cc.ddrpa.dorian.application.ship.result.ShipInfoQueryResult;
import cc.ddrpa.dorian.domain.ship.*;
import cc.ddrpa.dorian.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class ShipApplicationService {
    private static final Logger logger = LoggerFactory.getLogger(ShipApplicationService.class);

    @Value("${server.port}")
    private String ServicePort;

    private final RestTemplateBuilder restTemplateBuilder;
    private final ShipService shipService;
    private final ShipRepository shipRepository;
    private final CrewRepository crewRepository;

    public List<CrewInfoQueryResult> getAllCrewForShip(Integer shipId) {
        return crewRepository.findByShipId(shipId)
                .stream()
                .map(CrewInfoQueryResult::new)
                .toList();
    }

    public ShipInfoQueryResult getShipInfo(Integer shipId) {
        var ship = shipRepository.findById(shipId)
                .orElseThrow(() -> {
                    logger.warn("shipId {} not found", shipId);
                    return new ResourceNotFoundException("ship %s not found".formatted(shipId));
                });
        var crewList = restTemplateBuilder.build()
                .getForEntity(
                        "http://localhost:%s/ship/%d/crew".formatted(ServicePort, shipId),
                        CrewInfoQueryResult[].class)
                .getBody();
        if (Objects.isNull(crewList)) {
            return new ShipInfoQueryResult(ship, List.of());
        } else {
            return new ShipInfoQueryResult(ship, List.of(crewList));
        }
    }

    public ShipInfoQueryResult registerShip(RegisterShipCommand command) {
        Ship newShip = new Ship(command.name(), command.type(), command.owner());
        shipService.register(newShip);
        List<Crew> newCrews = command.crewList()
                .stream()
                .map(crew -> new Crew(crew.name(), newShip.getId()))
                .toList();
        shipService.registerCrews(newShip.getId(), newCrews);
        return new ShipInfoQueryResult(
                newShip,
                newCrews.stream()
                        .map(CrewInfoQueryResult::new)
                        .toList());
    }

    public void unregisterShip(Integer shipId) {
        shipService.unregister(shipId);
    }
}