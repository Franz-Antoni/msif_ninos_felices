package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.ProfessionalDto;
import pe.com.msif.model.Professional;
import pe.com.msif.service.ProfessionalService;

import java.util.List;

@RestController
@RequestMapping("api/professionals")
public class ProfessionalController {
    @Autowired
    private ProfessionalService professionalService;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfessionalDto> Create(@RequestBody ProfessionalDto dto) {
        Professional p = autoMapper.mapTo(dto, Professional.class);
        ProfessionalDto response = autoMapper.mapTo(professionalService.Save(p), ProfessionalDto.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProfessionalDto>> Read(@RequestParam(required = false) Boolean status) {
        List<ProfessionalDto> response = autoMapper.mapList(professionalService.FindAllByStatus(status), ProfessionalDto.class);
        if(response.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfessionalDto> Update(@PathVariable Integer id, @RequestBody ProfessionalDto dto) {
        Professional p = autoMapper.mapTo(dto, Professional.class);
        ProfessionalDto response = autoMapper.mapTo(professionalService.Update(id, p), ProfessionalDto.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Integer id) {
        professionalService.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

