// ...new file...
package pe.com.msif.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusUpdateDto {
    private String estado; // e.g. "Confirmada", "Rechazada"
    private Long profesionalId; // optional when approving
    private String motivoRechazo; // optional when rejecting

    // Campos para atención clínica
    private String motivoAtencion;
    private String evaluacionClinica;
    private String diagnostico;
    private String observaciones;
    private String planIntervencion;
}
