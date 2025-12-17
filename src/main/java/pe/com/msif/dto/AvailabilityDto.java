// ...new file...
package pe.com.msif.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AvailabilityDto {
    private boolean available;
    private List<LocalDateTime> conflictingDates; // simple list of conflicting fechaProgramada
}

