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
    private Long id;

    @Column(name = "paciente_id")
    private Long patientId;

    @Column(name = "profesional_id")
    private Long professionalId;

    @Column(name = "fecha_creacion")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "descripcion")
    private String description;

    @Column(name = "diagnostico")
    private String diagnosis;

    @Column(name = "esta_activo")
    private Boolean isActive = true;
}
