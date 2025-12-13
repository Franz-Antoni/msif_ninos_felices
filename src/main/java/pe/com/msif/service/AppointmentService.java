package pe.com.msif.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.msif.dto.AppointmentCalendarDto;
import pe.com.msif.exception.NotFoundException;
import pe.com.msif.model.Appointment;
import pe.com.msif.model.Patient;
import pe.com.msif.repository.AppointmentRepository;
import pe.com.msif.repository.PatientRepository;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private PatientRepository patientRepository;

    public Appointment save(Appointment a) {
        return appointmentRepository.save(a);
    }

    public Optional<Appointment> findById(Integer id) {
        return appointmentRepository.findById(id);
    }

    public List<AppointmentCalendarDto> getCalendarData(Optional<Integer> patientIdOpt, Integer month, Integer year) {
        // month: 1-12
        YearMonth ym = YearMonth.of(year, month);
        LocalDateTime from = ym.atDay(1).atStartOfDay();
        LocalDateTime to = ym.atEndOfMonth().atTime(23,59,59);

        List<Appointment> all = appointmentRepository.findByScheduledDateBetween(from, to);
        List<AppointmentCalendarDto> res = new ArrayList<>();

        for (Appointment a : all) {
            if (patientIdOpt.isPresent() && !patientIdOpt.get().equals(a.getPatientId())) continue;
            // Only active
            if (!Boolean.TRUE.equals(a.getIsActive())) continue;

            AppointmentCalendarDto dto = new AppointmentCalendarDto();
            dto.setId(a.getId());
            dto.setYear(a.getScheduledDate().getYear());
            dto.setMonth(a.getScheduledDate().getMonthValue());
            dto.setDay(a.getScheduledDate().getDayOfMonth());
            dto.setReason(a.getReason());
            // fetch patient name
            Optional<Patient> p = patientRepository.findById(a.getPatientId());
            dto.setPatientName(p.map(pt -> pt.getName() + " " + pt.getLastName()).orElse("<Unknown>"));
            dto.setStatus(a.getStatus() == null ? null : a.getStatus().getDbValue());
            dto.setIsActive(a.getIsActive());
            dto.setScheduledDate(a.getScheduledDate());

            res.add(dto);
        }

        return res;
    }
}

