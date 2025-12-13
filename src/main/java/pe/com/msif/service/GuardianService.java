package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.ConflictException;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.exception.BadRequestException;
import pe.com.msif.model.Guardian;
import pe.com.msif.repository.GuardianRepository;
import pe.com.msif.repository.RelationshipTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GuardianService {
    @Autowired
    private GuardianRepository guardianRepository;

    @Autowired
    private RelationshipTypeRepository relationshipTypeRepository;

    public Guardian Save(Guardian entity) {
        // Validar presence of relationshipTypeId
        if (entity.getRelationshipTypeId() == null) {
            throw new BadRequestException("relationshipTypeId is required");
        }

        // Validar que exista el tipo de relación
        Optional<?> relType = relationshipTypeRepository.findById(entity.getRelationshipTypeId());
        if (relType.isEmpty()) {
            throw new NotFoundException("Relationship type with id " + entity.getRelationshipTypeId() + " not found");
        }

        Optional<Guardian> guardian = guardianRepository.findByDni(entity.getDni());

        if(guardian.isPresent()) {
            throw new ConflictException("Ya existe un registro con este dni.");
        }

        return guardianRepository.save(entity);
    }

    public Guardian Update(Integer id, Guardian entity) {
        Optional<Guardian> entityExists = guardianRepository.findById(id);

        if(entityExists.isPresent()) {
            // Preserve registration date and active flag
            entity.setId(id);
            entity.setRegistrationDate(entityExists.get().getRegistrationDate());
            entity.setIsActive(entityExists.get().getIsActive());

            // If relationshipTypeId is not provided in the incoming entity, preserve existing
            if (entity.getRelationshipTypeId() == null) {
                entity.setRelationshipTypeId(entityExists.get().getRelationshipTypeId());
            } else {
                // If provided, validate it exists
                Optional<?> relType = relationshipTypeRepository.findById(entity.getRelationshipTypeId());
                if (relType.isEmpty()) {
                    throw new NotFoundException("Relationship type with id " + entity.getRelationshipTypeId() + " not found");
                }
            }

            return guardianRepository.save(entity);
        } else {
            throw new NotFoundException();
        }
    }

    public List<Guardian> findAllByStatus(Boolean status) {
        if(status == null) {
            return guardianRepository.findAll();
        }

        return guardianRepository.findAllByStatus(status);
    }

    public Optional<Guardian> FindById(Integer id) {
        Optional<Guardian> guardian = guardianRepository.findById(id);

        if(guardian.isEmpty()) {
            throw new NotFoundException("No sé encontro al apoderado.");
        }

        return guardian;
    }

    public void DeleteById(Integer id) {
        Optional<Guardian> entityExists = guardianRepository.findById(id);

        if(entityExists.isPresent()) {
            Guardian entity = entityExists.get();
            entity.setIsActive(false);

            guardianRepository.save(entity);
        } else {
            throw new NotFoundException();
        }
    }
}
