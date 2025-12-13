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
    private Integer id;
    @Column(name = "correo")
    private String email;
    @Column(name = "clave")
    private String password;
    @Column(name = "apoderado_id")
    private Integer guardianId;
    @Column(name = "profesional_id")
    private Integer professionalId;
    @Column(name = "fecha_creacion")
    private LocalDateTime registrationDate = LocalDateTime.now();
    @Column(name = "esta_activo")
    private Boolean isActive = true;
}
