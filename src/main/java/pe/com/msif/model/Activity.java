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
    private Integer id;

    @Column(name = "titulo", length = 100, nullable = false)
    private String title;

    @Column(name = "descripcion", length = 300, nullable = false)
    private String description;

    @Column(name = "sesion_terapia_id", nullable = false)
    private Integer therapySessionId;

    @Column(name = "estado", columnDefinition = "enum('Pendiente','En progreso','Completada','No lograda', 'Cancelada')")
    private String status;

    @Column(name = "nota_progreso", length = 300)
    private String progressNote;

    @Column(name = "esta_activo", nullable = false)
    private Boolean isActive = true;
}

