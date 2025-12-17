package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.TreatmentPlan;
import pe.com.msif.repository.TreatmentPlanRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TreatmentPlanService {
    @Autowired
    private TreatmentPlanRepository repository;

    public TreatmentPlan Save(TreatmentPlan entity) {
        return repository.save(entity);
    }

    public TreatmentPlan Update(Integer id, TreatmentPlan entity) {
        Optional<TreatmentPlan> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        entity.setId(id);
        entity.setIsActive(e.get().getIsActive());
        return repository.save(entity);
    }

    public List<TreatmentPlan> FindAllByStatus(Boolean status) {
        if (status == null) return repository.findAll();
        return repository.findAllByIsActive(status);
    }

    public Optional<TreatmentPlan> FindById(Integer id) {
        Optional<TreatmentPlan> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        return e;
    }

    public void DeleteById(Integer id) {
        Optional<TreatmentPlan> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        TreatmentPlan entity = e.get();
        entity.setIsActive(false);
        repository.save(entity);
    }
}

