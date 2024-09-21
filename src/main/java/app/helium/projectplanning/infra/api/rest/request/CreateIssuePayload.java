package app.helium.projectplanning.infra.api.rest.request;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateIssuePayload {
    private String summary;
    private String description;
    private List<String> attachmentURLs;
    private Integer pointEstimate;
    private UUID issueTypeId;
    private UUID issueStatusId;
    private Instant startDate;
    private Instant dueDate;
    private UUID reporterId;
    private UUID assigneeId;
}
