package pe.com.msif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.com.msif.model.TherapySession;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TherapySessionRepository extends JpaRepository<TherapySession, Long> {
    List<TherapySession> findAllByIsActive(Boolean isActive);

    // Buscar sesiones por paciente (uniendo plan_tratamiento) - sin rango
    @Query(value = "SELECT s.* FROM sesion_terapia s JOIN plan_tratamiento p ON p.id = s.plan_tratamiento_id WHERE p.paciente_id = :patientId AND s.esta_activo = :isActive", nativeQuery = true)
    List<TherapySession> findAllByPatientIdAndIsActive(@Param("patientId") Long patientId, @Param("isActive") Boolean isActive);

    // Buscar sesiones por paciente y rango de fecha
    @Query(value = "SELECT s.* FROM sesion_terapia s JOIN plan_tratamiento p ON p.id = s.plan_tratamiento_id WHERE p.paciente_id = :patientId AND s.fecha_asistencia BETWEEN :from AND :to AND s.esta_activo = :isActive", nativeQuery = true)
    List<TherapySession> findAllByPatientIdAndFechaAsistenciaBetweenAndIsActive(@Param("patientId") Long patientId, @Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("isActive") Boolean isActive);
}
