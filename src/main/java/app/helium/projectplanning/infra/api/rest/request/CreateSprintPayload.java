package app.helium.projectplanning.infra.api.rest.request;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSprintPayload {
    private String name;
    private String goal;
    private Instant startDate;
    private Instant dueDate;
}
