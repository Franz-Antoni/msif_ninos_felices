package pe.com.msif.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateCitaDto {
    private String razon;

    private Integer profesionalId;

    private LocalDateTime fechaProgramada;

    private String estado;

    private Boolean estaActivo;
}
