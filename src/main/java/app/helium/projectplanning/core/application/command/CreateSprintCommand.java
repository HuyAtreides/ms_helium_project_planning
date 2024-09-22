package app.helium.projectplanning.core.application.command;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateSprintCommand {
    private UUID id;
    private UUID projectId;
    private String name;
    private String goal;
    private Instant startDate;
    private Instant dueDate;
}
