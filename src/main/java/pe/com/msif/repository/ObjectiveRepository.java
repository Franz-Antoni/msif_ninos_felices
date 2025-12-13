package pe.com.msif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.msif.model.Objective;

import java.util.List;

@Repository
public interface ObjectiveRepository extends JpaRepository<Objective, Integer> {
    List<Objective> findAllByIsActive(Boolean isActive);
}

