package cc.ddrpa.dorian.domain.ship;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrewRepository extends JpaRepository<Crew, Integer> {
    List<Crew> findByShipId(Integer shipId);

    void deleteAllByShipId(Integer shipId);
}