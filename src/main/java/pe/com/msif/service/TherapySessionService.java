package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.TherapySession;
import pe.com.msif.repository.TherapySessionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TherapySessionService {
    @Autowired
    private TherapySessionRepository repository;

    public TherapySession Save(TherapySession entity) {
        return repository.save(entity);
    }

    public TherapySession Update(Integer id, TherapySession entity) {
        Optional<TherapySession> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        entity.setId(id);
        entity.setIsActive(e.get().getIsActive());
        return repository.save(entity);
    }

    public List<TherapySession> FindAllByStatus(Boolean status) {
        if (status == null) return repository.findAll();
        return repository.findAllByIsActive(status);
    }

    public Optional<TherapySession> FindById(Integer id) {
        Optional<TherapySession> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        return e;
    }

    public void DeleteById(Integer id) {
        Optional<TherapySession> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        TherapySession entity = e.get();
        entity.setIsActive(false);
        repository.save(entity);
    }
}

