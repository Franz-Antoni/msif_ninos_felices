package pe.com.msif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.msif.model.Specialty;

import java.util.List;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Integer> {
    List<Specialty> findAllByIsActive(Boolean isActive);
}

