package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.ObjectiveDto;
import pe.com.msif.dto.TreatmentPlanDto;
import pe.com.msif.model.Objective;
import pe.com.msif.model.TreatmentPlan;
import pe.com.msif.service.ObjectiveService;
import pe.com.msif.service.TreatmentPlanService;

import java.util.List;

@RestController
@RequestMapping("api/treatment-plans")
public class TreatmentPlanController {
    @Autowired
    private TreatmentPlanService service;
    @Autowired
    private ObjectiveService objectiveService;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TreatmentPlanDto> Create(@RequestBody TreatmentPlanDto dto) {
        TreatmentPlan e = autoMapper.mapTo(dto, TreatmentPlan.class);
        TreatmentPlanDto res = autoMapper.mapTo(service.Save(e), TreatmentPlanDto.class);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TreatmentPlanDto>> Read(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Long patientId
    ) {
        List<TreatmentPlan> plans;
        if (patientId != null) {
            plans = service.FindAllByPatientIdAndStatus(patientId, status);
        } else {
            plans = service.FindAllByStatus(status);
        }
        List<TreatmentPlanDto> res = autoMapper.mapList(plans, TreatmentPlanDto.class);
        if (res.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TreatmentPlanDto> Update(@PathVariable Long id, @RequestBody TreatmentPlanDto dto) {
        TreatmentPlan e = autoMapper.mapTo(dto, TreatmentPlan.class);
        TreatmentPlanDto res = autoMapper.mapTo(service.Update(id, e), TreatmentPlanDto.class);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Long id) {
        service.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/{id}/close")
    public ResponseEntity<TreatmentPlanDto> Close(@PathVariable Long id) {
        TreatmentPlan plan = service.Close(id);
        TreatmentPlanDto dto = autoMapper.mapTo(plan, TreatmentPlanDto.class);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping(path = "/{id}/reevaluate")
    public ResponseEntity<TreatmentPlanDto> Reevaluate(@PathVariable Long id) {
        TreatmentPlan plan = service.Reevaluate(id);
        TreatmentPlanDto dto = autoMapper.mapTo(plan, TreatmentPlanDto.class);
        return ResponseEntity.ok(dto);
    }

    // Objetivos anidados
    @PostMapping(path = "/{id}/objectives", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectiveDto> CreateObjective(@PathVariable Long id, @RequestBody ObjectiveDto dto) {
        Objective e = autoMapper.mapTo(dto, Objective.class);
        e.setTreatmentPlanId(id);
        Objective saved = objectiveService.Save(e);
        ObjectiveDto res = autoMapper.mapTo(saved, ObjectiveDto.class);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}/objectives", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ObjectiveDto>> ListObjectives(@PathVariable Long id) {
        List<Objective> list = objectiveService.FindAllByTreatmentPlan(id);
        List<ObjectiveDto> res = autoMapper.mapList(list, ObjectiveDto.class);
        if (res.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(res);
    }
}
