package app.helium.projectplanning.infra.api.rest.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueStatusPayload {
    private String name;
    private String description;
}
