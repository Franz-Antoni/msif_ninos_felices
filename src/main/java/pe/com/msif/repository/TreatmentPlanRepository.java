package pe.com.msif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.msif.model.TreatmentPlan;

import java.util.List;

@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Long> {
    List<TreatmentPlan> findAllByIsActive(Boolean isActive);

    // Nuevo: buscar por patientId
    List<TreatmentPlan> findAllByPatientId(Long patientId);

    // Nuevo: buscar por patientId y estActivo
    List<TreatmentPlan> findAllByPatientIdAndIsActive(Long patientId, Boolean isActive);
}
