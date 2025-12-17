package pe.com.msif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.dto.AppointmentCalendarDto;
import pe.com.msif.service.AppointmentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    // Calendar endpoint: acepta patientId o "ALL" o vacío para todos
    @GetMapping(path = "/calendar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentCalendarDto>> calendar(@RequestParam(required = false) String patient,
                                                                  @RequestParam Integer month,
                                                                  @RequestParam Integer year) {
        Optional<Integer> patientIdOpt = Optional.empty();
        if (patient != null && !patient.isBlank() && !"ALL".equalsIgnoreCase(patient)) {
            try {
                patientIdOpt = Optional.of(Integer.parseInt(patient));
            } catch (NumberFormatException ex) {
                // if not numeric, ignore and return all
                patientIdOpt = Optional.empty();
            }
        }

        List<AppointmentCalendarDto> result = appointmentService.getCalendarData(patientIdOpt, month, year);
        if (result.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(result);
    }
}

