package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.Patient;
import pe.com.msif.repository.PatientRepository;

import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    public Patient Save(Patient entity) {
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

    public Optional<Patient> ReadById(Integer id) {
        Optional<Patient> patient = patientRepository.findById(id);

        if(patient.isEmpty()) {
            throw new NotFoundException("No sé encontro al paciente.");
        }

        return patient;
    }

    public void Delete(Integer id) {
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
