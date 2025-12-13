package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
@Getter
@Setter
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "razon", length = 100, nullable = false)
    private String razon;

    @Column(name = "paciente_id", nullable = false)
    private Integer pacienteId;

    @Column(name = "profesional_id")
    private Integer profesionalId;

    @Column(name = "fecha_programada", nullable = false)
    private LocalDateTime fechaProgramada;

    // Enum Java con mapeo a los valores almacenados en la DB
    public enum Estado {
        PENDIENTE("Pendiente"),
        CONFIRMADA("Confirmada"),
        COMPLETADA("Completada"),
        CANCELADA("Cancelada"),
        NO_ASISTIO("No asistió");

        private final String dbValue;

        Estado(String dbValue) {
            this.dbValue = dbValue;
        }

        public String getDbValue() {
            return dbValue;
        }

        public static Estado fromDbValue(String dbValue) {
            for (Estado e : values()) {
                if (e.dbValue.equals(dbValue)) return e;
            }
            return null;
        }
    }

    // Mapeo que respeta la definición ENUM existente en la base de datos para evitar errores de validación
    @Column(name = "estado", columnDefinition = "enum('Pendiente','Confirmada','Completada','Cancelada','No asistió')")
    @Convert(converter = EstadoCitaConverter.class)
    private Estado estado = Estado.PENDIENTE;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "esta_activo", nullable = false)
    private Boolean estaActivo = true;

    // Convertidor como clase estática interna para mapear enum <-> valor DB
    @Converter(autoApply = false)
    public static class EstadoCitaConverter implements AttributeConverter<Estado, String> {
        @Override
        public String convertToDatabaseColumn(Estado attribute) {
            if (attribute == null) return null;
            return attribute.getDbValue();
        }

        @Override
        public Estado convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return Estado.fromDbValue(dbData);
        }
    }
}
