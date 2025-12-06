package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.ConflictException;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.Guardian;
import pe.com.msif.repository.GuardianRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GuardianService {
    @Autowired
    private GuardianRepository guardianRepository;

    public Guardian Save(Guardian entity) {
        Optional<Guardian> guardian = guardianRepository.findByDni(entity.getDni());

        if(guardian.isPresent()) {
            throw new ConflictException("Ya existe un registro con este dni.");
        }

        return guardianRepository.save(entity);
    }

    public Guardian Update(Integer id, Guardian entity) {
        Optional<Guardian> entityExists = guardianRepository.findById(id);

        if(entityExists.isPresent()) {
            entity.setId(id);
            entity.setRegistrationDate(entityExists.get().getRegistrationDate());
            entity.setIsActive(entityExists.get().getIsActive());

            return guardianRepository.save(entity);
        } else {
            throw new NotFoundException();
        }
    }

    public List<Guardian> FindAll() {
        return guardianRepository.findAll();
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
