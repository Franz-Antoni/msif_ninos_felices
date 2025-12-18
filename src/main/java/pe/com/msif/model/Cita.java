package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cita", schema = "public")
@Getter
@Setter
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "razon", length = 100, nullable = false)
    private String razon;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "profesional_id")
    private Long profesionalId;

    @Column(name = "fecha_programada", nullable = false)
    private LocalDateTime fechaProgramada;

    public enum Estado {
        PENDIENTE,
        CONFIRMADA,
        COMPLETADA,
        CANCELADA,
        NO_ASISTIO
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Estado estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "esta_activo", nullable = false)
    private Boolean estaActivo = true;

    @Transient
    private String motivoRechazo;

    public String getMotivoRechazo() {
        if (motivoRechazo != null && !motivoRechazo.trim().isEmpty()) {
            return motivoRechazo;
        }
        return this.razon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cita)) return false;
        Cita cita = (Cita) o;
        return Objects.equals(getId(), cita.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
