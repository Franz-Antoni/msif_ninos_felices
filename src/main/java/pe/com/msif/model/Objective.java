package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "objetivo")
@Getter
@Setter
public class Objective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String title;

    @Column(name = "descripion")
    private String description;

    @Column(name = "plan_tratamiento_id")
    private Long treatmentPlanId;

    @Column(name = "estado")
    private String status;

    @Column(name = "esta_activo")
    private Boolean isActive = true;
}
