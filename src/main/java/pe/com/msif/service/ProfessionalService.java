package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.Professional;
import pe.com.msif.repository.ProfessionalRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {
    @Autowired
    private ProfessionalRepository professionalRepository;

    public Professional Save(Professional entity) {
        return professionalRepository.save(entity);
    }

    public Professional Update(Integer id, Professional entity) {
        Optional<Professional> entityExists = professionalRepository.findById(id);

        if(entityExists.isPresent()) {
            entity.setId(id);
            entity.setIsActive(entityExists.get().getIsActive());
            entity.setRegistrationDate(entityExists.get().getRegistrationDate());
            return professionalRepository.save(entity);
        } else {
            throw new NotFoundException();
        }
    }

    public List<Professional> FindAllByStatus(Boolean status) {
        if(status == null) {
            return professionalRepository.findAll();
        }
        return professionalRepository.findAllByIsActive(status);
    }

    public Optional<Professional> FindById(Integer id) {
        Optional<Professional> entity = professionalRepository.findById(id);

        if(entity.isEmpty()) {
            throw new NotFoundException();
        }

        return entity;
    }

    public void DeleteById(Integer id) {
        Optional<Professional> entityExists = professionalRepository.findById(id);

        if(entityExists.isPresent()) {
            Professional entity = entityExists.get();
            entity.setIsActive(false);
            professionalRepository.save(entity);
        } else {
            throw new NotFoundException();
        }
    }
}

