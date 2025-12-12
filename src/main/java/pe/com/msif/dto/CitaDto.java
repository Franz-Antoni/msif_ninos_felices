package pe.com.msif.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CitaDto {
    private Integer id;
    private String razon;
    private Integer pacienteId;
    // DNI del paciente agregado para que el front pueda mostrarlo si lo necesita
    private String pacienteDni;
    private Integer profesionalId;
    private LocalDateTime fechaProgramada;
    private String estado;
    private LocalDateTime fechaCreacion;
    private Boolean estaActivo;
}
