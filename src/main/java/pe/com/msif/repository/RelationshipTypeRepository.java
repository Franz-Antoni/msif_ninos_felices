package pe.com.msif.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.com.msif.model.RelationshipType;

import java.util.List;

@Repository
public interface RelationshipTypeRepository extends JpaRepository<RelationshipType, Integer> {
    List<RelationshipType> findAllByIsActive(Boolean isActive);
}

