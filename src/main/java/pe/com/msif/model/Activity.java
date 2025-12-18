package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "actividad")
@Getter
@Setter
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String title;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "sesion_terapia_id")
    private Long therapySessionId;

    @Column(name = "estado")
    private String status;

    @Column(name = "nota_progreso")
    private String progressNote;

    @Column(name = "esta_activo")
    private Boolean isActive = true;
}
