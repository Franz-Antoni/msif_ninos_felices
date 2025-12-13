package pe.com.msif.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "objetivo")
@Getter
@Setter
public class Objective {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "titulo", length = 100, nullable = false)
    private String title;

    @Column(name = "descripion", length = 300)
    private String description;

    @Column(name = "plan_tratamiento_id", nullable = false)
    private Integer treatmentPlanId;

    public enum Status {
        PENDIENTE("Pendiente"), EN_PROGRESO("En progreso"), COMPLETADO("Completado"), CANCELADO("Cancelado"), EN_ESPERA("En espera");

        private final String dbValue;

        Status(String dbValue) { this.dbValue = dbValue; }
        public String getDbValue() { return dbValue; }
        public static Status fromDbValue(String dbValue) {
            for (Status s: values()) if (s.dbValue.equals(dbValue)) return s; return null;
        }
    }

    @Column(name = "estado", columnDefinition = "enum('Pendiente','En progreso','Completado','Cancelado','En espera')", nullable = false)
    @Convert(converter = ObjectiveStatusConverter.class)
    private Status status = Status.PENDIENTE;

    @Column(name = "esta_activo", nullable = false)
    private Boolean isActive = true;

    @Converter(autoApply = false)
    public static class ObjectiveStatusConverter implements AttributeConverter<Status, String> {
        @Override
        public String convertToDatabaseColumn(Status attribute) {
            if (attribute == null) return null; return attribute.getDbValue();
        }

        @Override
        public Status convertToEntityAttribute(String dbData) {
            if (dbData == null) return null; return Status.fromDbValue(dbData);
        }
    }
}

