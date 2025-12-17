package pe.com.msif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.msif.model.TherapySession;

import java.util.List;

@Repository
public interface TherapySessionRepository extends JpaRepository<TherapySession, Integer> {
    List<TherapySession> findAllByIsActive(Boolean isActive);
}

