package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.MedicalHistory;
import pe.com.msif.repository.MedicalHistoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalHistoryService {
    @Autowired
    private MedicalHistoryRepository repository;

    public MedicalHistory Save(MedicalHistory entity) {
        return repository.save(entity);
    }

    public MedicalHistory Update(Integer id, MedicalHistory entity) {
        Optional<MedicalHistory> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        entity.setId(id);
        entity.setIsActive(e.get().getIsActive());
        return repository.save(entity);
    }

    public List<MedicalHistory> FindAllByStatus(Boolean status) {
        if (status == null) return repository.findAll();
        return repository.findAllByIsActive(status);
    }

    public Optional<MedicalHistory> FindById(Integer id) {
        Optional<MedicalHistory> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        return e;
    }

    public void DeleteById(Integer id) {
        Optional<MedicalHistory> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        MedicalHistory entity = e.get();
        entity.setIsActive(false);
        repository.save(entity);
    }
}

