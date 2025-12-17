package pe.com.msif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.msif.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByPatientId(Integer patientId);
    List<Appointment> findByProfessionalId(Integer professionalId);
    List<Appointment> findByStatus(Appointment.Status status);
    List<Appointment> findByScheduledDateBetween(LocalDateTime from, LocalDateTime to);
}

