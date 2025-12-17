package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.Specialty;
import pe.com.msif.repository.SpecialtyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialtyService {
    @Autowired
    private SpecialtyRepository specialtyRepository;

    public Specialty Save(Specialty entity) {
        return specialtyRepository.save(entity);
    }

    public Specialty Update(Integer id, Specialty entity) {
        Optional<Specialty> entityExists = specialtyRepository.findById(id);

        if(entityExists.isPresent()) {
            entity.setId(id);
            entity.setIsActive(entityExists.get().getIsActive());
            return specialtyRepository.save(entity);
        } else {
            throw new NotFoundException();
        }
    }

    public List<Specialty> FindAllByStatus(Boolean status) {
        if(status == null) {
            return specialtyRepository.findAll();
        }
        return specialtyRepository.findAllByIsActive(status);
    }

    public Optional<Specialty> FindById(Integer id) {
        Optional<Specialty> entity = specialtyRepository.findById(id);

        if(entity.isEmpty()) {
            throw new NotFoundException();
        }

        return entity;
    }

    public void DeleteById(Integer id) {
        Optional<Specialty> entityExists = specialtyRepository.findById(id);

        if(entityExists.isPresent()) {
            Specialty entity = entityExists.get();
            entity.setIsActive(false);
            specialtyRepository.save(entity);
        } else {
            throw new NotFoundException();
        }
    }
}

