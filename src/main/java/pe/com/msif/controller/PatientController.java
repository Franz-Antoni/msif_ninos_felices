package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.PatientDto;
import pe.com.msif.model.Patient;
import pe.com.msif.service.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDto> Save(@RequestBody PatientDto patientDto)
    {
        Patient patient = autoMapper.mapTo(patientDto, Patient.class);
        PatientDto response = autoMapper.mapTo(patientService.Save(patient), PatientDto.class);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDto> Update(@PathVariable Integer id, @RequestBody PatientDto patientDto)
    {
        Patient patient = autoMapper.mapTo(patientDto, Patient.class);
        PatientDto response = autoMapper.mapTo(patientService.Update(id, patient), PatientDto.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Integer id)
    {
        patientService.Delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
