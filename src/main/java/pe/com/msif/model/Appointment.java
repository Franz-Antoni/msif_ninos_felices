package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "razon", length = 100, nullable = false)
    private String reason;

    @Column(name = "paciente_id", nullable = false)
    private Long patientId;

    @Column(name = "profesional_id")
    private Long professionalId;

    @Column(name = "fecha_programada", nullable = false)
    private LocalDateTime scheduledDate;

    @Column(name = "estado")
    private String status;

    @Column(name = "esta_activo")
    private Boolean isActive = true;
}
