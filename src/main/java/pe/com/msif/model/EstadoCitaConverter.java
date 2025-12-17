package pe.com.msif.model;

// Legacy converter renamed to avoid duplicate class name with the internal converter in Cita.java
public class EstadoCitaConverter{
    public static String convertToDatabaseColumn(Cita.Estado attribute) {
        if (attribute == null) return null;
        return attribute.getDbValue();
    }

    public static Cita.Estado convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return Cita.Estado.fromDbValue(dbData);
    }
}
