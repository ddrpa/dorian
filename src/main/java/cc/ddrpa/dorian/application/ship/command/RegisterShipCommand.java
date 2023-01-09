package cc.ddrpa.dorian.application.ship.command;

import cc.ddrpa.dorian.domain.ship.enums.ShipType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "注册船舶信息表单")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record RegisterShipCommand(
        @Schema(description = "船舶名称")
        @NotBlank
        String name,
        @NotNull
        @Schema(description = "船舶类型")
        ShipType type,
        @NotBlank
        @Schema(description = "所有者")
        String owner,
        @Valid
        @NotNull
        @Size(min = 1)
        @Schema(description = "船员清单")
        List<RegisterCrewCommand> crewList) {
}