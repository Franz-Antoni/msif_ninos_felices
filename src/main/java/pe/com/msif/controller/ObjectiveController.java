package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.ObjectiveDto;
import pe.com.msif.model.Objective;
import pe.com.msif.service.ObjectiveService;

import java.util.List;

@RestController
@RequestMapping("api/objectives")
public class ObjectiveController {
    @Autowired
    private ObjectiveService service;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectiveDto> Create(@RequestBody ObjectiveDto dto) {
        Objective e = autoMapper.mapTo(dto, Objective.class);
        ObjectiveDto res = autoMapper.mapTo(service.Save(e), ObjectiveDto.class);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ObjectiveDto>> Read(@RequestParam(required = false) Boolean status) {
        List<ObjectiveDto> res = autoMapper.mapList(service.FindAllByStatus(status), ObjectiveDto.class);
        if (res.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectiveDto> Update(@PathVariable Integer id, @RequestBody ObjectiveDto dto) {
        Objective e = autoMapper.mapTo(dto, Objective.class);
        ObjectiveDto res = autoMapper.mapTo(service.Update(id, e), ObjectiveDto.class);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Integer id) {
        service.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

