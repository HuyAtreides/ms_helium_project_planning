package app.helium.projectplanning.infra.datetime;

import app.helium.projectplanning.core.application.service.AbstractDateTimeService;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class DateTimeService implements AbstractDateTimeService {

    public Instant getCurrentInstant() {
        return Instant.now();
    }
}
