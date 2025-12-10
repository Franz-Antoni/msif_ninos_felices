package pe.com.msif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.com.msif.model.Guardian;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuardianRepository extends JpaRepository<Guardian, Integer> {
    @Query("SELECT g FROM Guardian g WHERE g.isActive =:status")
    List<Guardian> findAllByStatus(Boolean status);
    Optional<Guardian> findByDni(String dni);
}
