package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.Recommendation;
import pe.com.msif.repository.RecommendationRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendationService {
    @Autowired
    private RecommendationRepository repository;

    public Recommendation Save(Recommendation entity) {
        return repository.save(entity);
    }

    public Recommendation Update(Integer id, Recommendation entity) {
        Optional<Recommendation> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        entity.setId(id);
        entity.setIsActive(e.get().getIsActive());
        return repository.save(entity);
    }

    public List<Recommendation> FindAllByStatus(Boolean status) {
        if (status == null) return repository.findAll();
        return repository.findAllByIsActive(status);
    }

    public Optional<Recommendation> FindById(Integer id) {
        Optional<Recommendation> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        return e;
    }

    public void DeleteById(Integer id) {
        Optional<Recommendation> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        Recommendation entity = e.get();
        entity.setIsActive(false);
        repository.save(entity);
    }
}

