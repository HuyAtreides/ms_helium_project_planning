package app.helium.projectplanning.core.domain.factory;

import app.helium.projectplanning.core.domain.Bug;
import app.helium.projectplanning.core.domain.CustomIssue;
import app.helium.projectplanning.core.domain.Epic;
import app.helium.projectplanning.core.domain.Issue;
import app.helium.projectplanning.core.domain.SubTask;
import app.helium.projectplanning.core.domain.Task;
import app.helium.projectplanning.core.domain.UserStory;
import app.helium.projectplanning.core.domain.request.CreateNewIssueRequest;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class IssueFactory {

    public Issue createIssue(CreateNewIssueRequest request) {
        var project = request.getProject();
        var issueType = project.getIssueTypeById(request.getIssueTypeId());

        return switch (issueType.getType()) {
            case TASK -> createTask(request);
            case EPIC -> createEpic(request);
            case USER_STORY -> createUserStory(request);
            case SUB_TASK -> createSubTask(request);
            case CUSTOM -> createCustomIssue(request);
            case BUG -> createBug(request);
        };
    }

    private Task createTask(CreateNewIssueRequest request) {
        var project = request.getProject();
        UUID assigneeId = request.getAssigneeId() == null ? project.getDefaultAssigneeId()
                : request.getAssigneeId();
        UUID reporterId = request.getReporterId() == null ? project.getProjectLeadId()
                : request.getReporterId();
        Instant now = Instant.now();

        return Task.builder()
                .name(request.getIssueName())
                .summary(request.getSummary())
                .description(request.getDescription())
                .attachmentURLs(request.getAttachmentURLs())
                .pointEstimate(request.getPointEstimate())
                .assigneeId(assigneeId)
                .reporterId(reporterId)
                .lastUpdatedAt(now)
                .createdAt(now)
                .issueType(project.getIssueTypeById(request.getIssueTypeId()))
                .issueStatus(project.getIssueStatusById(request.getIssueStatusId()))
                .build();
    }

    private Epic createEpic(CreateNewIssueRequest request) {
        var project = request.getProject();
        UUID assigneeId = request.getAssigneeId() == null ? project.getDefaultAssigneeId()
                : request.getAssigneeId();
        UUID reporterId = request.getReporterId() == null ? project.getProjectLeadId()
                : request.getReporterId();
        Instant now = Instant.now();

        return Epic.builder()
                .name(request.getIssueName())
                .summary(request.getSummary())
                .description(request.getDescription())
                .attachmentURLs(request.getAttachmentURLs())
                .pointEstimate(request.getPointEstimate())
                .assigneeId(assigneeId)
                .reporterId(reporterId)
                .lastUpdatedAt(now)
                .createdAt(now)
                .issueType(project.getIssueTypeById(request.getIssueTypeId()))
                .issueStatus(project.getIssueStatusById(request.getIssueStatusId()))
                .build();
    }

    private UserStory createUserStory(CreateNewIssueRequest request) {
        var project = request.getProject();
        UUID assigneeId = request.getAssigneeId() == null ? project.getDefaultAssigneeId()
                : request.getAssigneeId();
        UUID reporterId = request.getReporterId() == null ? project.getProjectLeadId()
                : request.getReporterId();
        Instant now = Instant.now();

        return UserStory.builder()
                .name(request.getIssueName())
                .summary(request.getSummary())
                .description(request.getDescription())
                .attachmentURLs(request.getAttachmentURLs())
                .pointEstimate(request.getPointEstimate())
                .assigneeId(assigneeId)
                .reporterId(reporterId)
                .lastUpdatedAt(now)
                .createdAt(now)
                .issueType(project.getIssueTypeById(request.getIssueTypeId()))
                .issueStatus(project.getIssueStatusById(request.getIssueStatusId()))
                .build();
    }

    private SubTask createSubTask(CreateNewIssueRequest request) {
        var project = request.getProject();
        UUID assigneeId = request.getAssigneeId() == null ? project.getDefaultAssigneeId()
                : request.getAssigneeId();
        UUID reporterId = request.getReporterId() == null ? project.getProjectLeadId()
                : request.getReporterId();
        Instant now = Instant.now();

        return SubTask.builder()
                .name(request.getIssueName())
                .summary(request.getSummary())
                .description(request.getDescription())
                .attachmentURLs(request.getAttachmentURLs())
                .pointEstimate(request.getPointEstimate())
                .assigneeId(assigneeId)
                .reporterId(reporterId)
                .lastUpdatedAt(now)
                .createdAt(now)
                .issueType(project.getIssueTypeById(request.getIssueTypeId()))
                .issueStatus(project.getIssueStatusById(request.getIssueStatusId()))
                .build();
    }

    private CustomIssue createCustomIssue(CreateNewIssueRequest request) {
        var project = request.getProject();
        UUID assigneeId = request.getAssigneeId() == null ? project.getDefaultAssigneeId()
                : request.getAssigneeId();
        UUID reporterId = request.getReporterId() == null ? project.getProjectLeadId()
                : request.getReporterId();
        Instant now = Instant.now();

        return CustomIssue.builder()
                .name(request.getIssueName())
                .summary(request.getSummary())
                .description(request.getDescription())
                .attachmentURLs(request.getAttachmentURLs())
                .pointEstimate(request.getPointEstimate())
                .assigneeId(assigneeId)
                .reporterId(reporterId)
                .lastUpdatedAt(now)
                .createdAt(now)
                .issueType(project.getIssueTypeById(request.getIssueTypeId()))
                .issueStatus(project.getIssueStatusById(request.getIssueStatusId()))
                .build();
    }

    private Bug createBug(CreateNewIssueRequest request) {
        var project = request.getProject();
        UUID assigneeId = request.getAssigneeId() == null ? project.getDefaultAssigneeId()
                : request.getAssigneeId();
        UUID reporterId = request.getReporterId() == null ? project.getProjectLeadId()
                : request.getReporterId();
        Instant now = Instant.now();

        return Bug.builder()
                .name(request.getIssueName())
                .summary(request.getSummary())
                .description(request.getDescription())
                .attachmentURLs(request.getAttachmentURLs())
                .pointEstimate(request.getPointEstimate())
                .assigneeId(assigneeId)
                .reporterId(reporterId)
                .lastUpdatedAt(now)
                .createdAt(now)
                .issueType(project.getIssueTypeById(request.getIssueTypeId()))
                .issueStatus(project.getIssueStatusById(request.getIssueStatusId()))
                .build();
    }
}
