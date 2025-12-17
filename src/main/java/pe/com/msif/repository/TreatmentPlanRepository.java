package pe.com.msif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.msif.model.TreatmentPlan;

import java.util.List;

@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Integer> {
    List<TreatmentPlan> findAllByIsActive(Boolean isActive);
}

