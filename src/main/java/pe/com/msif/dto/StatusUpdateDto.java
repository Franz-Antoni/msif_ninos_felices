// ...new file...
package pe.com.msif.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusUpdateDto {
    private String estado; // e.g. "Confirmada", "Rechazada"
    private Integer profesionalId; // optional when approving
    private String motivoRechazo; // optional when rejecting
}

