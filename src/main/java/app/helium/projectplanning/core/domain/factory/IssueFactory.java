package app.helium.projectplanning.core.domain.factory;

import app.helium.projectplanning.core.domain.model.Bug;
import app.helium.projectplanning.core.domain.model.CustomIssue;
import app.helium.projectplanning.core.domain.model.DateRange;
import app.helium.projectplanning.core.domain.model.Epic;
import app.helium.projectplanning.core.domain.model.Issue;
import app.helium.projectplanning.core.domain.model.Issue.IssueBuilder;
import app.helium.projectplanning.core.domain.model.SubTask;
import app.helium.projectplanning.core.domain.model.Task;
import app.helium.projectplanning.core.domain.model.UserStory;
import app.helium.projectplanning.core.domain.request.CreateIssueRequest;
import java.time.Instant;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IssueFactory {

    public Issue createIssue(CreateIssueRequest request) {
        var project = request.getProject();
        var issueType = project.getIssueTypeById(request.getIssueTypeId());

        return switch (issueType.getSupportedType()) {
            case TASK -> createTask(request);
            case EPIC -> createEpic(request);
            case USER_STORY -> createUserStory(request);
            case SUB_TASK -> createSubTask(request);
            case CUSTOM -> createCustomIssue(request);
            case BUG -> createBug(request);
        };
    }

    private <C extends Issue, B extends IssueBuilder<C, B>> Issue.IssueBuilder<C, B> populateCommonIssueFields(
            CreateIssueRequest request, Issue.IssueBuilder<C, B> builder
    ) {
        var project = request.getProject();
        UUID assigneeId = request.getAssigneeId() == null ? project.getDefaultAssigneeId()
                : request.getAssigneeId();
        UUID reporterId = request.getReporterId() == null ? project.getProjectLeadId()
                : request.getReporterId();
        Instant now = request.getCreatedAt();

        return builder
                .id(UUID.randomUUID())
                .name(request.getIssueName())
                .summary(request.getSummary())
                .description(request.getDescription())
                .attachmentURLs(request.getAttachmentURLs())
                .pointEstimate(request.getPointEstimate())
                .creatorId(request.getCreatorId())
                .lastUpdatedById(request.getCreatorId())
                .assigneeId(assigneeId)
                .reporterId(reporterId)
                .dateRange(DateRange.from(request.getStartDate(), request.getDueDate()))
                .lastUpdatedAt(now)
                .projectId(project.getId())
                .createdAt(now)
                .type(project.getIssueTypeById(request.getIssueTypeId()))
                .status(project.getIssueStatusById(request.getIssueStatusId()));
    }

    private Task createTask(CreateIssueRequest request) {
        return populateCommonIssueFields(request, Task.builder()).build();
    }

    private Epic createEpic(CreateIssueRequest request) {
        return populateCommonIssueFields(request, Epic.builder()).build();
    }

    private UserStory createUserStory(CreateIssueRequest request) {
        return populateCommonIssueFields(request, UserStory.builder()).build();
    }

    private SubTask createSubTask(CreateIssueRequest request) {
        return populateCommonIssueFields(request, SubTask.builder()).build();
    }

    private CustomIssue createCustomIssue(CreateIssueRequest request) {
        return populateCommonIssueFields(request, CustomIssue.builder()).build();
    }

    private Bug createBug(CreateIssueRequest request) {
        return populateCommonIssueFields(request, Bug.builder()).build();
    }
}
