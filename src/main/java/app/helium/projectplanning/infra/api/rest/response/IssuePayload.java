package app.helium.projectplanning.infra.api.rest.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssuePayload {
    private UUID id;
    private String summary;
    private String description;
    private String name;
    private List<String> attachmentURLs;
    private Instant createdAt;
    private Instant lastUpdatedAt;
    private UUID lastUpdatedById;
    private Integer pointEstimate;
    private UUID assigneeId;
    private UUID reporterId;
    private UUID projectId;
    private UUID creatorId;
    private IssueTypePayload type;
    private IssueStatusPayload status;

    @JsonUnwrapped
    private DateRangePayload dateRange;
}
