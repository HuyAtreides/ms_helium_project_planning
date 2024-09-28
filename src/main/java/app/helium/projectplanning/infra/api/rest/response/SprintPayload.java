package app.helium.projectplanning.infra.api.rest.response;

import app.helium.projectplanning.core.domain.model.Issue;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SprintPayload {
    private UUID id;
    private String name;
    private String goal;
    @JsonUnwrapped
    private DateRangePayload dateRange;
    private Set<IssuePayload> issues;
}
