package pe.com.msif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.msif.model.Cita;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByPacienteId(Long pacienteId);
    List<Cita> findByProfesionalId(Long profesionalId);
    List<Cita> findByEstado(Cita.Estado estado);
    List<Cita> findByFechaProgramadaBetween(LocalDateTime from, LocalDateTime to);

    List<Cita> findByProfesionalIdAndFechaProgramadaAndEstaActivoTrue(Long profesionalId, LocalDateTime fechaProgramada);

    // buscar citas activas del profesional en un rango (por si se quiere comprobar solapamiento)
    List<Cita> findByProfesionalIdAndFechaProgramadaBetweenAndEstaActivoTrue(Long profesionalId, LocalDateTime from, LocalDateTime to);
}
