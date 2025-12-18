package pe.com.msif.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.msif.dto.ActivityDto;
import pe.com.msif.dto.ObjectiveDto;
import pe.com.msif.dto.TherapySessionDto;
import pe.com.msif.dto.PatientDto;
import pe.com.msif.service.ActivityService;
import pe.com.msif.service.ObjectiveService;
import pe.com.msif.service.PatientService;
import pe.com.msif.service.TherapySessionService;
import pe.com.msif.config.AutoMapper;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/progress-report")
public class ProgressReportController {
    @Autowired
    private TherapySessionService sessionService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ObjectiveService objectiveService;
    @Autowired
    private PatientService patientService;
    @Autowired
    private AutoMapper autoMapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getReport(
            @RequestParam Long patientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        PatientDto patient = autoMapper.mapTo(patientService.FindById(patientId).get(), PatientDto.class);
        List<TherapySessionDto> sessions = autoMapper.mapList(sessionService.FindByPatient(patientId, from, to), TherapySessionDto.class);
        // actividades por sesion
        Map<Long, List<ActivityDto>> activitiesBySession = new HashMap<>();
        for (TherapySessionDto s : sessions) {
            List<ActivityDto> acts = autoMapper.mapList(activityService.FindAllByTherapySession(s.getId()), ActivityDto.class);
            activitiesBySession.put(s.getId(), acts);
        }
        // objetivos del plan(es) del paciente: tomar todos los treatment plans y objetivos
        List<ObjectiveDto> objectives = autoMapper.mapList(objectiveService.FindAllByStatus(true), ObjectiveDto.class);

        Map<String, Object> res = new HashMap<>();
        res.put("patient", patient);
        res.put("sessions", sessions);
        res.put("activitiesBySession", activitiesBySession);
        res.put("objectives", objectives);
        return ResponseEntity.ok(res);
    }

    @PostMapping(path = "/pdf", produces = "application/pdf")
    public ResponseEntity<byte[]> generatePdf(
            @RequestParam Long patientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) throws Exception {
        Map<String, Object> report = getReport(patientId, from, to).getBody();
        PatientDto patient = (PatientDto) report.get("patient");
        List<TherapySessionDto> sessions = (List<TherapySessionDto>) report.get("sessions");

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);
            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                 PDPageContentStream cs = new PDPageContentStream(doc, page)) {
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 14);
                cs.newLineAtOffset(50, 750);
                cs.showText("Progress report for: " + patient.getName() + " " + patient.getLastName());
                cs.newLineAtOffset(0, -20);
                cs.setFont(PDType1Font.HELVETICA, 12);
                cs.showText("Sessions: " + sessions.size());
                cs.newLineAtOffset(0, -20);
                int count = 0;
                for (TherapySessionDto s : sessions) {
                    if (count++ > 20) break; // evitar overflow en la página
                    cs.showText("- " + s.getAttendanceDate() + " : " + (s.getNote() == null ? "" : s.getNote()));
                    cs.newLineAtOffset(0, -15);
                }
                cs.endText();
                doc.save(out);
                byte[] bytes = out.toByteArray();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "progress_report.pdf");
                return ResponseEntity.ok().headers(headers).body(bytes);
            }
        }
    }
}

