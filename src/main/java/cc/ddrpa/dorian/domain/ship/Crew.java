package cc.ddrpa.dorian.domain.ship;

import cc.ddrpa.dorian.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "tbl_crew")
public class Crew extends BaseEntity {
    @Column(nullable = false, length = 63)

    private String name;
    @Column(nullable = false)
    private Integer shipId;
}