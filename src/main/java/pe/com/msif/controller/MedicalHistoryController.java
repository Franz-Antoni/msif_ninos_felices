package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.MedicalHistoryDto;
import pe.com.msif.model.MedicalHistory;
import pe.com.msif.service.MedicalHistoryService;

import java.util.List;

@RestController
@RequestMapping("api/medical-histories")
public class MedicalHistoryController {
    @Autowired
    private MedicalHistoryService service;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicalHistoryDto> Create(@RequestBody MedicalHistoryDto dto) {
        MedicalHistory e = autoMapper.mapTo(dto, MedicalHistory.class);
        MedicalHistoryDto res = autoMapper.mapTo(service.Save(e), MedicalHistoryDto.class);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicalHistoryDto>> Read(@RequestParam(required = false) Boolean status) {
        List<MedicalHistoryDto> res = autoMapper.mapList(service.FindAllByStatus(status), MedicalHistoryDto.class);
        if (res.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicalHistoryDto> Update(@PathVariable Integer id, @RequestBody MedicalHistoryDto dto) {
        MedicalHistory e = autoMapper.mapTo(dto, MedicalHistory.class);
        MedicalHistoryDto res = autoMapper.mapTo(service.Update(id, e), MedicalHistoryDto.class);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Integer id) {
        service.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

