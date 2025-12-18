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

    public TreatmentPlan Update(Long id, TreatmentPlan entity) {
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

    public List<TreatmentPlan> FindAllByPatientIdAndStatus(Long patientId, Boolean status) {
        if (status == null) return repository.findAllByPatientId(patientId);
        return repository.findAllByPatientIdAndIsActive(patientId, status);
    }

    public Optional<TreatmentPlan> FindById(Long id) {
        Optional<TreatmentPlan> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        return e;
    }

    public void DeleteById(Long id) {
        Optional<TreatmentPlan> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        TreatmentPlan entity = e.get();
        entity.setIsActive(false);
        repository.save(entity);
    }

    public TreatmentPlan Close(Long id) {
        Optional<TreatmentPlan> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        TreatmentPlan plan = e.get();
        plan.setIsActive(false);
        return repository.save(plan);
    }

    public TreatmentPlan Reevaluate(Long id) {
        Optional<TreatmentPlan> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        TreatmentPlan plan = e.get();
        // aquí podrías setear alguna bandera o actualizar evaluación; por ahora la dejamos activa y no cambiamos isActive
        return repository.save(plan);
    }
}
