package app.helium.projectplanning.infra.datetime;

import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class DateTimeService {

    public Instant getCurrentInstant() {
        return Instant.now();
    }
}
