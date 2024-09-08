package app.helium.projectplanning.core.domain.request;

import app.helium.projectplanning.core.domain.Project;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class CreateNewIssueRequest {
    private String summary;
    private String description;
    private List<String> attachmentURLs;
    private Integer pointEstimate;
    private UUID issueTypeId;
    private String issueName;
    private UUID issueStatusId;
    private Project project;
    private UUID reporterId;
    private UUID assigneeId;
}
