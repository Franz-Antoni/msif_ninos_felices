package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.ActivityDto;
import pe.com.msif.model.Activity;
import pe.com.msif.service.ActivityService;

import java.util.List;

@RestController
@RequestMapping("api/activities")
public class ActivityController {
    @Autowired
    private ActivityService service;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityDto> Create(@RequestBody ActivityDto dto) {
        Activity e = autoMapper.mapTo(dto, Activity.class);
        ActivityDto res = autoMapper.mapTo(service.Save(e), ActivityDto.class);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ActivityDto>> Read(@RequestParam(required = false) Boolean status) {
        List<ActivityDto> res = autoMapper.mapList(service.FindAllByStatus(status), ActivityDto.class);
        if (res.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActivityDto> Update(@PathVariable Integer id, @RequestBody ActivityDto dto) {
        Activity e = autoMapper.mapTo(dto, Activity.class);
        ActivityDto res = autoMapper.mapTo(service.Update(id, e), ActivityDto.class);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Integer id) {
        service.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

