package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.TherapySessionDto;
import pe.com.msif.model.TherapySession;
import pe.com.msif.service.TherapySessionService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/therapy-sessions")
public class TherapySessionController {
    @Autowired
    private TherapySessionService service;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TherapySessionDto> Create(@RequestBody TherapySessionDto dto) {
        TherapySession e = autoMapper.mapTo(dto, TherapySession.class);
        TherapySessionDto res = autoMapper.mapTo(service.Save(e), TherapySessionDto.class);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TherapySessionDto>> Read(
            @RequestParam(required = false) Boolean status,
            @RequestParam(required = false) Long patientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        List<TherapySession> sessions;
        if (patientId != null) {
            sessions = service.FindByPatient(patientId, from, to);
        } else {
            sessions = autoMapper.mapList(service.FindAllByStatus(status), TherapySession.class);
            return new ResponseEntity<>(autoMapper.mapList(sessions, TherapySessionDto.class), HttpStatus.OK);
        }
        List<TherapySessionDto> res = autoMapper.mapList(sessions, TherapySessionDto.class);
        if (res.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TherapySessionDto> Update(@PathVariable Long id, @RequestBody TherapySessionDto dto) {
        TherapySession e = autoMapper.mapTo(dto, TherapySession.class);
        TherapySessionDto res = autoMapper.mapTo(service.Update(id, e), TherapySessionDto.class);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Long id) {
        service.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
