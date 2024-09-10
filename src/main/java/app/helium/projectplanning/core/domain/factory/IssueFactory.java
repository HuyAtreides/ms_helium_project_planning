package app.helium.projectplanning.core.domain.factory;

import app.helium.projectplanning.core.domain.Bug;
import app.helium.projectplanning.core.domain.CustomIssue;
import app.helium.projectplanning.core.domain.Epic;
import app.helium.projectplanning.core.domain.Issue;
import app.helium.projectplanning.core.domain.Issue.IssueBuilder;
import app.helium.projectplanning.core.domain.SubTask;
import app.helium.projectplanning.core.domain.Task;
import app.helium.projectplanning.core.domain.UserStory;
import app.helium.projectplanning.core.domain.request.CreateIssueRequest;
import java.time.Instant;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class IssueFactory {

    public Issue createIssue(CreateIssueRequest request) {
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

    public <C extends Issue, B extends IssueBuilder<C, B>> Issue.IssueBuilder<C, B> populateCommonIssueFields(
            CreateIssueRequest request, Issue.IssueBuilder<C, B> builder
    ) {
        var project = request.getProject();
        UUID assigneeId = request.getAssigneeId() == null ? project.getDefaultAssigneeId()
                : request.getAssigneeId();
        UUID reporterId = request.getReporterId() == null ? project.getProjectLeadId()
                : request.getReporterId();
        Instant now = Instant.now();

        return builder
                .name(request.getIssueName())
                .summary(request.getSummary())
                .description(request.getDescription())
                .attachmentURLs(request.getAttachmentURLs())
                .pointEstimate(request.getPointEstimate())
                .assigneeId(assigneeId)
                .reporterId(reporterId)
                .startDate(request.getStartDate())
                .dueDate(request.getDueDate())
                .lastUpdatedAt(now)
                .createdAt(now)
                .issueType(project.getIssueTypeById(request.getIssueTypeId()))
                .issueStatus(project.getIssueStatusById(request.getIssueStatusId()));
    }

    private Task createTask(CreateIssueRequest request) {
        Task task = populateCommonIssueFields(request, Task.builder()).build();
        task.updateDueDateBaseOnPointEstimateAndStartDate();

        return task;
    }

    private Epic createEpic(CreateIssueRequest request) {
        Epic epic = populateCommonIssueFields(request, Epic.builder()).build();
        epic.updateDueDateBaseOnPointEstimateAndStartDate();

        return epic;
    }

    private UserStory createUserStory(CreateIssueRequest request) {
        UserStory userStory = populateCommonIssueFields(request, UserStory.builder()).build();
        userStory.updateDueDateBaseOnPointEstimateAndStartDate();

        return userStory;
    }

    private SubTask createSubTask(CreateIssueRequest request) {
        SubTask subTask = populateCommonIssueFields(request, SubTask.builder()).build();
        subTask.updateDueDateBaseOnPointEstimateAndStartDate();

        return subTask;
    }

    private CustomIssue createCustomIssue(CreateIssueRequest request) {
        CustomIssue customIssue = populateCommonIssueFields(request, CustomIssue.builder()).build();
        customIssue.updateDueDateBaseOnPointEstimateAndStartDate();

        return customIssue;
    }

    private Bug createBug(CreateIssueRequest request) {
        Bug bug = populateCommonIssueFields(request, Bug.builder()).build();
        bug.updateDueDateBaseOnPointEstimateAndStartDate();

        return bug;
    }
}
