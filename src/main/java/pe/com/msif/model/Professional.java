package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "profesional")
@Getter
@Setter
public class Professional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre", length = 50, nullable = false)
    private String firstName;

    @Column(name = "apelido", length = 50, nullable = false)
    private String lastName;

    @Column(name = "especialidad_id", nullable = false)
    private Integer specialtyId;

    @Column(name = "telefono", length = 9, nullable = false)
    private String phone;

    @Column(name = "fecha_registro")
    private LocalDateTime registrationDate = LocalDateTime.now();

    @Column(name = "esta_activo", nullable = false)
    private Boolean isActive = true;
}

