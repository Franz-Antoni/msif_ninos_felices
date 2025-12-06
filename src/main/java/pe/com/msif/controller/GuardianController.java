package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.GuardianDto;
import pe.com.msif.model.Guardian;
import pe.com.msif.service.GuardianService;

import java.util.List;

@RestController
@RequestMapping("/guardian")
public class GuardianController {
    @Autowired
    private GuardianService guardianService;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuardianDto> Create(@RequestBody GuardianDto guardianDto)
    {
        Guardian guardian = autoMapper.mapTo(guardianDto, Guardian.class);
        GuardianDto response = autoMapper.mapTo(guardianService.Save(guardian), GuardianDto.class);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GuardianDto>> Read()
    {
        List<GuardianDto> response = autoMapper.mapList(guardianService.FindAll(), GuardianDto.class);

        if(response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuardianDto> Update(@PathVariable Integer id, @RequestBody GuardianDto guardianDto)
    {
        Guardian guardian = autoMapper.mapTo(guardianDto, Guardian.class);
        GuardianDto response = autoMapper.mapTo(guardianService.Update(id, guardian), GuardianDto.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Integer id)
    {
        guardianService.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
