package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.Objective;
import pe.com.msif.repository.ObjectiveRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ObjectiveService {
    @Autowired
    private ObjectiveRepository repository;

    public Objective Save(Objective entity) {
        return repository.save(entity);
    }

    public Objective Update(Integer id, Objective entity) {
        Optional<Objective> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        entity.setId(id);
        entity.setIsActive(e.get().getIsActive());
        return repository.save(entity);
    }

    public List<Objective> FindAllByStatus(Boolean status) {
        if (status == null) return repository.findAll();
        return repository.findAllByIsActive(status);
    }

    public Optional<Objective> FindById(Integer id) {
        Optional<Objective> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        return e;
    }

    public void DeleteById(Integer id) {
        Optional<Objective> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        Objective entity = e.get();
        entity.setIsActive(false);
        repository.save(entity);
    }
}

