package app.helium.projectplanning.core.domain.request;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSprintRequest {
    private UUID id;
    private String name;
    private String goal;
    private Instant startDate;
    private Instant dueDate;
    private Instant createdAt;
    private UUID creatorId;
}
