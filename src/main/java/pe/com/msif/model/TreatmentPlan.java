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
    private Long id;

    @Column(name = "paciente_id")
    private Long patientId;

    @Column(name = "profesional_id")
    private Long professionalId;

    @Column(name = "fecha_inicio")
    private LocalDate startDate;

    @Column(name = "fecha_final")
    private LocalDate endDate;

    @Column(name = "evaluacion")
    private String evaluation;

    @Column(name = "esta_activo")
    private Boolean isActive = true;
}
