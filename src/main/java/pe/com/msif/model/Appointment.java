package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "razon", length = 100, nullable = false)
    private String reason;

    @Column(name = "paciente_id", nullable = false)
    private Integer patientId;

    @Column(name = "profesional_id")
    private Integer professionalId;

    @Column(name = "fecha_programada", nullable = false)
    private LocalDateTime scheduledDate;

    public enum Status {
        PENDIENTE("Pendiente"),
        CONFIRMADA("Confirmada"),
        COMPLETADA("Completada"),
        CANCELADA("Cancelada"),
        NO_ASISTIO("No asistió");

        private final String dbValue;

        Status(String dbValue) { this.dbValue = dbValue; }
        public String getDbValue() { return dbValue; }
        public static Status fromDbValue(String dbValue) {
            for (Status s : values()) if (s.dbValue.equals(dbValue)) return s;
            return null;
        }
    }

    @Column(name = "estado", columnDefinition = "enum('Pendiente','Confirmada','Completada','Cancelada','No asistió')")
    @Convert(converter = AppointmentStatusConverter.class)
    private Status status = Status.PENDIENTE;

    @Column(name = "fecha_creacion")
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "esta_activo", nullable = false)
    private Boolean isActive = true;

    @Converter(autoApply = false)
    public static class AppointmentStatusConverter implements AttributeConverter<Status, String> {
        @Override
        public String convertToDatabaseColumn(Status attribute) {
            if (attribute == null) return null;
            return attribute.getDbValue();
        }

        @Override
        public Status convertToEntityAttribute(String dbData) {
            if (dbData == null) return null;
            return Status.fromDbValue(dbData);
        }
    }
}

