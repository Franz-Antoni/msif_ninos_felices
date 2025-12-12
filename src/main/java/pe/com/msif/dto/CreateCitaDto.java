package pe.com.msif.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CreateCitaDto {
    private String razon;

    // Ahora se recibe el DNI del paciente y se valida que exista en la tabla paciente
    private String pacienteDni;

    // Campo opcional para compatibilidad: si el front manda pacienteId en lugar de pacienteDni
    private Integer pacienteId;

    // profesionalId no se acepta en la creación: se asigna después al aprobar la cita

    private LocalDateTime fechaProgramada;
}
