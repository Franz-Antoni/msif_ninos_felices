package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.Activity;
import pe.com.msif.repository.ActivityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {
    @Autowired
    private ActivityRepository repository;

    public Activity Save(Activity entity) {
        return repository.save(entity);
    }

    public Activity Update(Integer id, Activity entity) {
        Optional<Activity> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        entity.setId(id);
        entity.setIsActive(e.get().getIsActive());
        return repository.save(entity);
    }

    public List<Activity> FindAllByStatus(Boolean status) {
        if (status == null) return repository.findAll();
        return repository.findAllByIsActive(status);
    }

    public Optional<Activity> FindById(Integer id) {
        Optional<Activity> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        return e;
    }

    public void DeleteById(Integer id) {
        Optional<Activity> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        Activity entity = e.get();
        entity.setIsActive(false);
        repository.save(entity);
    }
}

