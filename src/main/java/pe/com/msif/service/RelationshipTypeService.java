package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.RelationshipType;
import pe.com.msif.repository.RelationshipTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RelationshipTypeService {
    @Autowired
    private RelationshipTypeRepository repository;

    public RelationshipType Save(RelationshipType entity) {
        return repository.save(entity);
    }

    public RelationshipType Update(Integer id, RelationshipType entity) {
        Optional<RelationshipType> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        entity.setId(id);
        entity.setIsActive(e.get().getIsActive());
        return repository.save(entity);
    }

    public List<RelationshipType> FindAllByStatus(Boolean status) {
        if (status == null) return repository.findAll();
        return repository.findAllByIsActive(status);
    }

    public Optional<RelationshipType> FindById(Integer id) {
        Optional<RelationshipType> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        return e;
    }

    public void DeleteById(Integer id) {
        Optional<RelationshipType> e = repository.findById(id);
        if (e.isEmpty()) throw new NotFoundException();
        RelationshipType entity = e.get();
        entity.setIsActive(false);
        repository.save(entity);
    }
}

