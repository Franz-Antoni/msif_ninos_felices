package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.TreatmentPlanDto;
import pe.com.msif.model.TreatmentPlan;
import pe.com.msif.service.TreatmentPlanService;

import java.util.List;

@RestController
@RequestMapping("api/treatment-plans")
public class TreatmentPlanController {
    @Autowired
    private TreatmentPlanService service;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TreatmentPlanDto> Create(@RequestBody TreatmentPlanDto dto) {
        TreatmentPlan e = autoMapper.mapTo(dto, TreatmentPlan.class);
        TreatmentPlanDto res = autoMapper.mapTo(service.Save(e), TreatmentPlanDto.class);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TreatmentPlanDto>> Read(@RequestParam(required = false) Boolean status) {
        List<TreatmentPlanDto> res = autoMapper.mapList(service.FindAllByStatus(status), TreatmentPlanDto.class);
        if (res.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TreatmentPlanDto> Update(@PathVariable Integer id, @RequestBody TreatmentPlanDto dto) {
        TreatmentPlan e = autoMapper.mapTo(dto, TreatmentPlan.class);
        TreatmentPlanDto res = autoMapper.mapTo(service.Update(id, e), TreatmentPlanDto.class);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Integer id) {
        service.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

