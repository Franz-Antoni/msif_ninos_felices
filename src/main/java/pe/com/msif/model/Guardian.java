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
    private Long id;
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
    private LocalDateTime registrationDate;
    @Column(name = "esta_activo", nullable = false)
    private Boolean isActive;

    @PrePersist
    public void prePersist() {
        if (this.isActive == null) {
            this.isActive = true;
        }
        if (this.registrationDate == null) {
            this.registrationDate = LocalDateTime.now();
        }
    }
}
