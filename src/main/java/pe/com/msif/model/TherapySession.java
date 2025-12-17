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
    private Integer id;

    @Column(name = "plan_tratamiento_id", nullable = false)
    private Integer treatmentPlanId;

    @Column(name = "cita_id", nullable = false)
    private Integer appointmentId;

    @Column(name = "fecha_asistencia")
    private LocalDateTime attendanceDate;

    @Column(name = "nota", length = 300)
    private String note;

    @Column(name = "esta_activo", nullable = false)
    private Boolean isActive = true;
}

