package cc.ddrpa.dorian.domain.ship;

import cc.ddrpa.dorian.domain.BaseEntity;
import cc.ddrpa.dorian.domain.ship.enums.ShipStatus;
import cc.ddrpa.dorian.domain.ship.enums.ShipType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "tbl_ship")
public class Ship extends BaseEntity {
    @Column(nullable = false, length = 63)
    private String name;
    @Column(nullable = false, length = 31)
    @Enumerated(EnumType.STRING)
    private ShipType type;
    @Column(nullable = false)
    private LocalDateTime lastReportTime;
    @Column(nullable = false, length = 127)
    private String lastKnownLocation;
    @Convert(converter = ShipStatus.Converter.class)
    @Column(columnDefinition = "tinyint", nullable = false)
    private ShipStatus status;
    @Column(nullable = false, length = 63)
    private String owner;

    public Ship(String name, ShipType type, String owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
        this.lastReportTime = LocalDateTime.now();
        this.lastKnownLocation = "Fortune-9";
        this.status = ShipStatus.ANCHORED;
    }
}