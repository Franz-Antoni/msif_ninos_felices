package pe.com.msif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.msif.model.Professional;

import java.util.List;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {
    List<Professional> findAllByIsActive(Boolean isActive);
}

