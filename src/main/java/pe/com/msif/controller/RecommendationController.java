package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.config.AutoMapper;
import pe.com.msif.dto.RecommendationDto;
import pe.com.msif.model.Recommendation;
import pe.com.msif.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("api/recommendations")
public class RecommendationController {
    @Autowired
    private RecommendationService service;
    @Autowired
    private AutoMapper autoMapper;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecommendationDto> Create(@RequestBody RecommendationDto dto) {
        Recommendation e = autoMapper.mapTo(dto, Recommendation.class);
        RecommendationDto res = autoMapper.mapTo(service.Save(e), RecommendationDto.class);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecommendationDto>> Read(@RequestParam(required = false) Boolean status) {
        List<RecommendationDto> res = autoMapper.mapList(service.FindAllByStatus(status), RecommendationDto.class);
        if (res.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecommendationDto> Update(@PathVariable Integer id, @RequestBody RecommendationDto dto) {
        Recommendation e = autoMapper.mapTo(dto, Recommendation.class);
        RecommendationDto res = autoMapper.mapTo(service.Update(id, e), RecommendationDto.class);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> Delete(@PathVariable Integer id) {
        service.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

