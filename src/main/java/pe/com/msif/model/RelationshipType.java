package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tipo_relacion")
@Getter
@Setter
public class RelationshipType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", length = 100, nullable = false)
    private String name;

    @Column(name = "esta_activo", nullable = false)
    private Boolean isActive = true;
}

