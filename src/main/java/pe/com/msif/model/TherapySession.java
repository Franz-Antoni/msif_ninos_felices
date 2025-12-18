package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "sesion_terapia")
@Getter
@Setter
public class TherapySession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "plan_tratamiento_id")
    private Long treatmentPlanId;

    @Column(name = "cita_id")
    private Long appointmentId;

    @Column(name = "fecha_asistencia")
    private LocalDateTime attendanceDate;

    @Column(name = "nota")
    private String note;

    @Column(name = "esta_activo")
    private Boolean isActive = true;
}
