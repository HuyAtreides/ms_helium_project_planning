package app.helium.projectplanning.infra.api.rest.response;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DateRangePayload {
    private Instant startDate;
    private Instant dueDate;
}
