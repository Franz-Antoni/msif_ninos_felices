package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "paciente")
@Getter
@Setter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellido")
    private String lastName;
    @Column(name = "genero")
    private Boolean gender;
    @Column(name = "dni")
    private String dni;
    @Column(name = "fecha_nacimiento")
    private LocalDate birthDate;
    @Column(name = "nivel_autismo", columnDefinition = "ENUM('Leve','Moderado','Grave')")
    private String autismLevel;
    @Column(name = "apoderado_id")
    private Integer guardianId;
    @Column(name = "fecha_registro")
    private LocalDateTime registrationDate = LocalDateTime.now();
    @Column(name = "esta_activo")
    private Boolean isActive = true;
}
