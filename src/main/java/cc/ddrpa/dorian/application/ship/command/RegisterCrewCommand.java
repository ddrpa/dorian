package cc.ddrpa.dorian.application.ship.command;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "注册船员信息表单")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record RegisterCrewCommand(
        @Schema(description = "姓名")
        @NotBlank
        String name
) {
}