package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "plan_tratamiento")
@Getter
@Setter
public class TreatmentPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "paciente_id", nullable = false)
    private Integer patientId;

    @Column(name = "profesional_id", nullable = false)
    private Integer professionalId;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate startDate;

    @Column(name = "fecha_final", nullable = false)
    private LocalDate endDate;

    @Column(name = "evaluacion", length = 300)
    private String evaluation;

    @Column(name = "esta_activo", nullable = false)
    private Boolean isActive = true;
}

