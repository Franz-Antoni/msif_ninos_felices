// ...new file...
package pe.com.msif.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pe.com.msif.model.Cita;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    public void notifyCitaApproved(Cita cita) {
        // placeholder: integrar con email/queue
        log.info("Notification: Cita {} aprobada para pacienteId={} profesionalId={} fecha={}", cita.getId(), cita.getPacienteId(), cita.getProfesionalId(), cita.getFechaProgramada());
    }

    public void notifyCitaRejected(Cita cita) {
        log.info("Notification: Cita {} rechazada, motivo='{}'", cita.getId(), cita.getMotivoRechazo());
    }
}

