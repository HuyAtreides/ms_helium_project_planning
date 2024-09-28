package app.helium.projectplanning.test.unit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Named.named;

import app.helium.projectplanning.core.domain.constant.SupportedIssueStatus;
import app.helium.projectplanning.core.domain.constant.SupportedIssueType;
import app.helium.projectplanning.core.domain.factory.IssueFactory;
import app.helium.projectplanning.core.domain.model.Bug;
import app.helium.projectplanning.core.domain.model.CustomIssue;
import app.helium.projectplanning.core.domain.model.Epic;
import app.helium.projectplanning.core.domain.model.Issue;
import app.helium.projectplanning.core.domain.model.IssueStatus;
import app.helium.projectplanning.core.domain.model.IssueType;
import app.helium.projectplanning.core.domain.model.Project;
import app.helium.projectplanning.core.domain.model.SubTask;
import app.helium.projectplanning.core.domain.model.Task;
import app.helium.projectplanning.core.domain.model.UserStory;
import app.helium.projectplanning.core.domain.request.CreateIssueRequest;
import app.helium.projectplanning.test.shared.constant.CommonTestConstant;
import app.helium.projectplanning.test.shared.validator.DomainEntityValidator;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateIssueTest {

    private final IssueFactory issueFactory = new IssueFactory();

    static private final UUID issueStatusId = UUID.fromString("97880669-bde4-4737-8e0d-cd8e14b6588f");
    static private final UUID issueTypeId = UUID.fromString("7231b12c-fd1a-4312-9a2f-aca28bf765dc");
    static private final Project project = Project.builder()
            .id(CommonTestConstant.DEFAULT_TEST_PROJECT_ID)
            .issueStatuses(
                    Set.of(IssueStatus.builder().id(issueStatusId).name("in_progress").build())
            )
            .issueTypes(
                    Set.of(IssueType.builder().id(issueTypeId).name("user_story").build())
            )
            .build();

    @ParameterizedTest
    @MethodSource("invalidCreateIssueRequest")
    void issue_should_be_unsuccessfully_created_when_given_invalid_data(
            CreateIssueRequest request
    ) {
        assertThrows(ConstraintViolationException.class, () -> {
            DomainEntityValidator.validate(issueFactory.createIssue(request));
        });
    }

    @Test
    void issue_should_be_unsuccessfully_created_when_given_issue_status_not_in_project() {
        UUID issueStatusId = UUID.fromString("97880669-bde4-4737-8e0d-cd8e14b6588f");
        UUID issueTypeId = UUID.fromString("7231b12c-fd1a-4312-9a2f-aca28bf765dc");
        Project project = Project.builder()
                .id(UUID.randomUUID())
                .issueStatuses(
                        Set.of(IssueStatus.builder().id(issueStatusId).name("in_progress").build())
                )
                .issueTypes(
                        Set.of(IssueType.builder().id(issueTypeId).name("user_story").build())
                )
                .build();

        assertThrows(
                NoSuchElementException.class,
                () -> issueFactory.createIssue(
                        CreateIssueRequest
                                .builder()
                                .summary("New Issue")
                                .issueName("HE-1104")
                                .creatorId(UUID.randomUUID())
                                .project(project)
                                .issueStatusId(
                                        UUID.fromString("cbe1d257-79c6-469a-83f0-1dc4d98f32d0"))
                                .issueTypeId(issueTypeId)
                                .build()
                )
        );
    }

    @Test
    void issue_should_be_unsuccessfully_created_when_given_issue_type_not_in_project() {
        UUID issueStatusId = UUID.fromString("97880669-bde4-4737-8e0d-cd8e14b6588f");
        UUID issueTypeId = UUID.fromString("7231b12c-fd1a-4312-9a2f-aca28bf765dc");
        Project project = Project.builder()
                .id(UUID.randomUUID())
                .issueStatuses(
                        Set.of(IssueStatus.builder().id(issueStatusId).name("in_progress").build())
                )
                .issueTypes(
                        Set.of(IssueType.builder().id(issueTypeId).name("user_story").build())
                )
                .build();

        assertThrows(
                NoSuchElementException.class,
                () -> issueFactory.createIssue(
                        CreateIssueRequest
                                .builder()
                                .summary("New Issue")
                                .issueName("HE-1104")
                                .creatorId(UUID.randomUUID())
                                .project(project)
                                .issueStatusId(issueStatusId)
                                .issueTypeId(
                                        UUID.fromString("cbe1d257-79c6-469a-83f0-1dc4d98f32d0"))
                                .build()
                )
        );
    }

    @ParameterizedTest
    @MethodSource("validCreateIssueRequest")
    void issue_should_be_created_successfully_when_given_valid_data(CreateIssueRequest request) {
        assertDoesNotThrow(() -> {
            DomainEntityValidator.validate(issueFactory.createIssue(request));
        });
    }

    @ParameterizedTest
    @MethodSource("createIssueRequestWithDifferentIssueStatus")
    void issue_should_be_created_successfully_with_correct_status_when_given_valid_data(
            CreateIssueRequest request,
            SupportedIssueStatus expectedIssueStatus
    ) {
        var issue = issueFactory.createIssue(request);
        assertEquals(expectedIssueStatus, issue.getStatus().getSupportedStatus());
    }

    @ParameterizedTest
    @MethodSource("createIssueRequestWithDifferentIssueType")
    void issue_should_be_created_successfully_with_correct_type_when_given_valid_data(
        CreateIssueRequest request,
        Class<? extends Issue> createdIssueClass,
        SupportedIssueType expectedIssueType
    ) {

        var issue = issueFactory.createIssue(request);
        assertEquals(expectedIssueType, issue.getType().getSupportedType());
        assertSame(createdIssueClass, issue.getClass());
    }

    @Test
    void issue_should_be_created_with_default_lead_id_and_assignee_id_from_project() {
        UUID issueStatusId = UUID.fromString("97880669-bde4-4737-8e0d-cd8e14b6588f");
        UUID issueTypeId = UUID.fromString("7231b12c-fd1a-4312-9a2f-aca28bf765dc");
        UUID assigneeId = UUID.randomUUID();
        UUID leadId = UUID.randomUUID();
        Project project = Project.builder()
                .id(UUID.randomUUID())
                .issueStatuses(
                        Set.of(IssueStatus.builder().id(issueStatusId).name("in_progress").build())
                )
                .issueTypes(
                        Set.of(IssueType.builder().id(issueTypeId).name("user_story").build())
                )
                .defaultAssigneeId(assigneeId)
                .projectLeadId(leadId)
                .build();

        var issue = issueFactory.createIssue(
                CreateIssueRequest.builder()
                        .issueStatusId(issueStatusId)
                        .issueTypeId(issueTypeId)
                        .project(project).build()
        );
        assertEquals(assigneeId, issue.getAssigneeId());
        assertEquals(leadId, issue.getReporterId());
    }

    private static Stream<Arguments> invalidCreateIssueRequest() {
        return Stream.of(
                Arguments.of(
                        named("invalid date range",
                                CreateIssueRequest
                                        .builder()
                                        .project(project)
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                        .creatorId(UUID.randomUUID())
                                        .summary("New Issue")
                                        .issueName("HE-1104")
                                        .startDate(Instant.parse("2025-09-04T00:00:00Z"))
                                        .dueDate(Instant.parse("2025-04-11T00:00:00Z"))
                                        .build()
                        )
                ),
                Arguments.of(
                        named("due date in the past",
                                CreateIssueRequest
                                        .builder()
                                        .project(project)
                                        .issueStatusId(issueStatusId)
                                        .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                        .issueTypeId(issueTypeId)
                                        .creatorId(UUID.randomUUID())
                                        .summary("New Issue")
                                        .issueName("HE-1104")
                                        .startDate(Instant.parse("2025-04-01T00:00:00Z"))
                                        .dueDate(Instant.parse("2025-04-10T00:00:00Z"))
                                        .build()
                        )
                ),
                Arguments.of(
                        named("attachment urls contain invalid url",
                                CreateIssueRequest
                                        .builder()
                                        .summary("New Issue")
                                        .issueName("HE-1104")
                                        .project(project)
                                        .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .creatorId(UUID.randomUUID())
                                        .attachmentURLs(List.of(
                                                "https://media.helium@12.com",
                                                "https://media.helium.com"
                                        ))
                                        .build()
                        )
                ),
                Arguments.of(
                        named("estimate point is negative",
                                CreateIssueRequest
                                        .builder()
                                        .project(project)
                                        .summary("New Issue")
                                        .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                        .issueName("HE-1104")
                                        .creatorId(UUID.randomUUID())
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .pointEstimate(-1)
                                        .build()
                        )
                ),
                Arguments.of(
                        named("estimate point is zero",
                                CreateIssueRequest
                                        .builder()
                                        .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                        .summary("New Issue")
                                        .issueName("HE-1104")
                                        .creatorId(UUID.randomUUID())
                                        .project(project)
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .pointEstimate(0)
                                        .build()
                        )
                ),
                Arguments.of(
                        named("missing summary",
                                CreateIssueRequest
                                        .builder()
                                        .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                        .issueName("HE-1104")
                                        .creatorId(UUID.randomUUID())
                                        .project(project)
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .build()
                        )
                ),
                Arguments.of(
                        named("missing name",
                                CreateIssueRequest
                                        .builder()
                                        .summary("New Issue")
                                        .creatorId(UUID.randomUUID())
                                        .project(project)
                                        .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .build()
                        )
                ),
                Arguments.of(
                        named("missing creator id",
                                CreateIssueRequest
                                        .builder()
                                        .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                        .summary("New Issue")
                                        .issueName("HE-1104")
                                        .creatorId(null)
                                        .project(project)
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .build()
                        )
                )
        );
    }

    private static Stream<Arguments> validCreateIssueRequest() {
        return Stream.of(
                Arguments.of(
                        named("has all required fields",
                                CreateIssueRequest
                                        .builder()
                                        .summary("New Issue")
                                        .issueName("HE-1104")
                                        .creatorId(UUID.randomUUID())
                                        .project(project)
                                        .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .build())
                ),
                Arguments.of(
                        named("has valid point estimate",
                                CreateIssueRequest
                                        .builder()
                                        .summary("New Issue")
                                        .issueName("HE-1104")
                                        .creatorId(UUID.randomUUID())
                                        .project(project)
                                        .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                        .pointEstimate(2)
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .build())
                ),
                Arguments.of(
                        named("has valid date range",
                                CreateIssueRequest
                                        .builder()
                                        .summary("New Issue")
                                        .issueName("HE-1104")
                                        .startDate(Instant.parse("2025-04-11T00:00:00Z"))
                                        .dueDate(Instant.parse("2025-04-14T00:00:00Z"))
                                        .pointEstimate(1)
                                        .creatorId(UUID.randomUUID())
                                        .project(project)
                                        .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .build())
                ),
                Arguments.of(
                        named("has valid attachment urls",
                                CreateIssueRequest
                                        .builder()
                                        .summary("New Issue")
                                        .issueName("HE-1104")
                                        .attachmentURLs(List.of(
                                                "https://media.helium.com/path",
                                                "https://media.helium.com/media_name",
                                                "https://media.helium.com/media-name"
                                        ))
                                        .startDate(Instant.parse("2025-04-11T00:00:00Z"))
                                        .dueDate(Instant.parse("2025-04-14T00:00:00Z"))
                                        .pointEstimate(1)
                                        .creatorId(UUID.randomUUID())
                                        .project(project)
                                        .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .build())
                )
        );
    }

    private static Stream<Arguments> createIssueRequestWithDifferentIssueStatus() {
        UUID toDo = UUID.randomUUID();
        UUID inProgress = UUID.randomUUID();
        UUID done = UUID.randomUUID();
        UUID inTesting = UUID.randomUUID();
        UUID userStory = UUID.randomUUID();
        Project project = Project.builder()
                .id(UUID.randomUUID())
                .issueStatuses(
                        Set.of(
                                IssueStatus.builder().id(toDo).name("to_do").build(),
                                IssueStatus.builder().id(inProgress).name("in_progress").build(),
                                IssueStatus.builder().id(done).name("done").build(),
                                IssueStatus.builder().id(inTesting).name("in_testing").custom(true).build()
                        )
                )
                .issueTypes(
                        Set.of(IssueType.builder().id(userStory).name("user_story").build())
                )
                .build();

        return Stream.of(
                Arguments.of(
                        named("create issue with to do status",
                                CreateIssueRequest.builder()
                                        .project(project)
                                        .issueTypeId(userStory)
                                        .issueStatusId(toDo)
                                        .build()
                        ),
                        SupportedIssueStatus.TO_DO
                ),
                Arguments.of(
                        named("create issue with in progress status",
                                CreateIssueRequest.builder()
                                        .project(project)
                                        .issueTypeId(userStory)
                                        .issueStatusId(inProgress)
                                        .build()
                        ),
                        SupportedIssueStatus.IN_PROGRESS
                ),
                Arguments.of(
                        named("create issue with done status",
                                CreateIssueRequest.builder()
                                        .project(project)
                                        .issueTypeId(userStory)
                                        .issueStatusId(done)
                                        .build()
                        ),
                        SupportedIssueStatus.DONE
                ),
                Arguments.of(
                        named("create issue with in testing status",
                                CreateIssueRequest.builder()
                                        .project(project)
                                        .issueTypeId(userStory)
                                        .issueStatusId(inTesting)
                                        .build()
                        ),
                        SupportedIssueStatus.CUSTOM
                )
        );
    }

    private static Stream<Arguments> createIssueRequestWithDifferentIssueType() {
        UUID toDo = UUID.randomUUID();
        UUID bug = UUID.randomUUID();
        UUID userStory = UUID.randomUUID();
        UUID task = UUID.randomUUID();
        UUID epic = UUID.randomUUID();
        UUID subTask = UUID.randomUUID();
        UUID qa = UUID.randomUUID();

        Project project = Project.builder()
                .id(UUID.randomUUID())
                .issueStatuses(
                        Set.of(
                                IssueStatus.builder().id(toDo).name("to_do").build()
                        )
                )
                .issueTypes(
                        Set.of(
                                IssueType.builder().id(userStory).name("user_story").build(),
                                IssueType.builder().id(task).name("task").build(),
                                IssueType.builder().id(subTask).name("sub_task").build(),
                                IssueType.builder().id(epic).name("epic").build(),
                                IssueType.builder().id(bug).name("bug").build(),
                                IssueType.builder().id(qa).name("Quality Assurance").custom(true).build()
                        )
                )
                .build();

        return Stream.of(
                Arguments.of(
                        named("create user story",
                                CreateIssueRequest.builder()
                                        .project(project)
                                        .issueTypeId(userStory)
                                        .issueStatusId(toDo)
                                        .build()
                        ),
                        UserStory.class,
                        SupportedIssueType.USER_STORY
                ),
                Arguments.of(
                        named("create task",
                                CreateIssueRequest.builder()
                                        .project(project)
                                        .issueTypeId(task)
                                        .issueStatusId(toDo)
                                        .build()
                        ),
                        Task.class,
                        SupportedIssueType.TASK
                ),
                Arguments.of(
                        named("create sub task",
                                CreateIssueRequest.builder()
                                        .project(project)
                                        .issueTypeId(subTask)
                                        .issueStatusId(toDo)
                                        .build()
                        ),
                        SubTask.class,
                        SupportedIssueType.SUB_TASK
                ),
                Arguments.of(
                        named("create epic",
                                CreateIssueRequest.builder()
                                        .project(project)
                                        .issueTypeId(epic)
                                        .issueStatusId(toDo)
                                        .build()
                        ),
                        Epic.class,
                        SupportedIssueType.EPIC
                ),

                Arguments.of(
                        named("create bug",
                                CreateIssueRequest.builder()
                                        .project(project)
                                        .issueTypeId(bug)
                                        .issueStatusId(toDo)
                                        .build()
                        ),
                        Bug.class,
                        SupportedIssueType.BUG
                ),

                Arguments.of(
                        named("create custom issue",
                                CreateIssueRequest.builder()
                                        .project(project)
                                        .issueTypeId(qa)
                                        .issueStatusId(toDo)
                                        .build()
                        ),
                        CustomIssue.class,
                        SupportedIssueType.CUSTOM
                )
        );
    }
}
