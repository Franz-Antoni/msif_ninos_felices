package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.RelationshipTypeDto;
import pe.com.msif.model.RelationshipType;
import pe.com.msif.service.RelationshipTypeService;

import java.util.List;

@RestController
@RequestMapping("api/relationship-types")
public class RelationshipTypeController {
    @Autowired
    private RelationshipTypeService service;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelationshipTypeDto> Create(@RequestBody RelationshipTypeDto dto) {
        RelationshipType e = autoMapper.mapTo(dto, RelationshipType.class);
        RelationshipTypeDto res = autoMapper.mapTo(service.Save(e), RelationshipTypeDto.class);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RelationshipTypeDto>> Read(@RequestParam(required = false) Boolean status) {
        List<RelationshipTypeDto> res = autoMapper.mapList(service.FindAllByStatus(status), RelationshipTypeDto.class);
        if (res.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RelationshipTypeDto> Update(@PathVariable Integer id, @RequestBody RelationshipTypeDto dto) {
        RelationshipType e = autoMapper.mapTo(dto, RelationshipType.class);
        RelationshipTypeDto res = autoMapper.mapTo(service.Update(id, e), RelationshipTypeDto.class);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Integer id) {
        service.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

