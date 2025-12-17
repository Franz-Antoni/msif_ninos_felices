package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.SpecialtyDto;
import pe.com.msif.model.Specialty;
import pe.com.msif.service.SpecialtyService;

import java.util.List;

@RestController
@RequestMapping("api/specialties")
public class SpecialtyController {
    @Autowired
    private SpecialtyService specialtyService;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SpecialtyDto> Create(@RequestBody SpecialtyDto dto) {
        Specialty e = autoMapper.mapTo(dto, Specialty.class);
        SpecialtyDto response = autoMapper.mapTo(specialtyService.Save(e), SpecialtyDto.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SpecialtyDto>> Read(@RequestParam(required = false) Boolean status) {
        List<SpecialtyDto> response = autoMapper.mapList(specialtyService.FindAllByStatus(status), SpecialtyDto.class);
        if(response.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SpecialtyDto> Update(@PathVariable Integer id, @RequestBody SpecialtyDto dto) {
        Specialty e = autoMapper.mapTo(dto, Specialty.class);
        SpecialtyDto response = autoMapper.mapTo(specialtyService.Update(id, e), SpecialtyDto.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Integer id) {
        specialtyService.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

