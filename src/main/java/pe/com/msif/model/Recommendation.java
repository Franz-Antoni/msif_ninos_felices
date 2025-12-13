package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recomendacion")
@Getter
@Setter
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String name;

    @Column(name = "descripcion", length = 300)
    private String description;

    @Column(name = "historial_clinico_id", nullable = false)
    private Integer medicalHistoryId;

    @Column(name = "esta_activo", nullable = false)
    private Boolean isActive = true;
}

