package app.helium.projectplanning.core.domain.request;

import app.helium.projectplanning.core.domain.model.Project;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateIssueRequest {
    private String summary;
    private String description;
    private List<String> attachmentURLs;
    private Integer pointEstimate;
    private Instant createdAt;
    private UUID issueTypeId;
    private String issueName;
    private UUID issueStatusId;
    private Instant startDate;
    private Instant dueDate;
    private Project project;
    private UUID creatorId;
    private UUID reporterId;
    private UUID assigneeId;
}
