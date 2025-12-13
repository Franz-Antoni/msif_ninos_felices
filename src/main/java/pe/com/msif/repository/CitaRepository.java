package pe.com.msif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.msif.model.Cita;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findByPacienteId(Integer pacienteId);
    List<Cita> findByProfesionalId(Integer profesionalId);
    List<Cita> findByEstado(Cita.Estado estado);
    List<Cita> findByFechaProgramadaBetween(LocalDateTime from, LocalDateTime to);
}
