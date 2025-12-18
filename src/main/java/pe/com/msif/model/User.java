package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "correo")
    private String email;

    @Column(name = "clave")
    private String password;

    @Column(name = "apoderado_id")
    private Long guardianId;

    @Column(name = "profesional_id")
    private Long professionalId;

    @Column(name = "fecha_creacion")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "esta_activo")
    private Boolean isActive = true;
}
