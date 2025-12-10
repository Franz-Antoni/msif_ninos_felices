package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.ConflictException;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.Patient;
import pe.com.msif.repository.GuardianRepository;
import pe.com.msif.repository.PatientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private GuardianRepository guardianRepository;

    public Patient Save(Patient entity) {
        Optional<Patient> patient = patientRepository.findByDni(entity.getDni());

        if(patient.isPresent()) {
            throw new ConflictException("Ya existe un registro con este dni.");
        }

        return patientRepository.save(entity);
    }

    public Patient Update(Integer id, Patient entity) {
        Optional<Patient> entityExists = patientRepository.findById(id);

        if(entityExists.isPresent() && entityExists.get().getIsActive()) {
            entity.setId(id);
            entity.setRegistrationDate(entityExists.get().getRegistrationDate());
            entity.setIsActive(entityExists.get().getIsActive());

            return patientRepository.save(entity);
        } else {
            throw new NotFoundException();
        }
    }

    public List<Patient> FindAllByStatus(Boolean status) {
        if(status == null) {
            return patientRepository.findAll();
        }

        return patientRepository.findAllByStatus(status);
    }

    public Optional<Patient> FindById(Integer id) {
        Optional<Patient> patient = patientRepository.findById(id);

        if(patient.isEmpty()) {
            throw new NotFoundException("No sé encontro al paciente.");
        }

        return patient;
    }

    public void DeleteById(Integer id) {
        Optional<Patient> entityExists = patientRepository.findById(id);

        if(entityExists.isPresent()) {
            Patient entity = entityExists.get();
            entity.setIsActive(false);

            patientRepository.save(entity);
        } else {
            throw new NotFoundException();
        }
    }
}
