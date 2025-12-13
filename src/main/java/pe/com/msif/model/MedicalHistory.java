package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_clinico")
@Getter
@Setter
public class MedicalHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "paciente_id", nullable = false)
    private Integer patientId;

    @Column(name = "profesional_id", nullable = false)
    private Integer professionalId;

    @Column(name = "fecha_creacion")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "descripcion", length = 300)
    private String description;

    @Column(name = "diagnostico", length = 100)
    private String diagnosis;

    @Column(name = "esta_activo", nullable = false)
    private Boolean isActive = true;
}

