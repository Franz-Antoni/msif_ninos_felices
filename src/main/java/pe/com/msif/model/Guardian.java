package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "apoderado")
@Getter
@Setter
public class Guardian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String name;
    @Column(name = "apellido")
    private String lastName;
    @Column(name = "telefono")
    private String telephone;
    @Column(name = "direccion")
    private String address;
    @Column(name = "dni")
    private String dni;
    @Column(name = "fecha_registro")
    private LocalDateTime registrationDate = LocalDateTime.now();
    @Column(name = "esta_activo")
    private Boolean isActive = true;
}
