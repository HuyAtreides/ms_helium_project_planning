package app.helium.projectplanning.test.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.helium.projectplanning.core.application.command.CreateIssueCommand;
import app.helium.projectplanning.core.application.service.ProjectPlanningService;
import app.helium.projectplanning.test.shared.constant.CommonTestConstant;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;

@IntegrationTest
public class CreateIssueIntegrationTest {

    @Autowired
    private ProjectPlanningService service;

    static private final UUID issueStatusId = UUID.fromString(
            "b8a8a0bd-888b-45b5-845d-1c4b316c9288");
    static private final UUID issueTypeId = UUID.fromString("f998b0aa-050d-41ad-bcad-e7b4e2b5a17e");

    @Test
    @Sql(
            scripts = {
                    "classpath:/sql_scripts/clear_all_tables.sql",
                    "classpath:/sql_scripts/insert_project.sql"
            },
            config = @SqlConfig(transactionMode = TransactionMode.ISOLATED)
    )
    public void issue_should_be_created_when_given_valid_create_issue_command() {
        var command = CreateIssueCommand.builder()
                .assigneeId(UUID.randomUUID())
                .summary("New Issue")
                .projectId(CommonTestConstant.DEFAULT_TEST_PROJECT_ID)
                .startDate(CommonTestConstant.FIXED_NOW_INSTANT)
                .dueDate(Instant.parse("2025-05-07T17:15:00Z"))
                .reporterId(CommonTestConstant.DEFAULT_USER_ID)
                .assigneeId(CommonTestConstant.DEFAULT_USER_ID)
                .description("Implement create issue")
                .attachmentURLs(List.of("https://media.helium.com/requirements.pdf", "https://media.helium.com/figma"))
                .pointEstimate(2)
                .issueStatusId(issueStatusId)
                .issueTypeId(issueTypeId)
                .build();

        var issue = service.createIssue(command);
        assertNotNull(issue.getName());
        assertNotNull(issue.getId());
    }

    @Test
    @Sql(
            scripts = {
                    "classpath:/sql_scripts/clear_all_tables.sql",
                    "classpath:/sql_scripts/insert_project.sql"
            },
            config = @SqlConfig(transactionMode = TransactionMode.ISOLATED)
    )
    public void issue_can_not_be_created_when_given_project_can_not_be_found() {
        var command = CreateIssueCommand.builder()
                .assigneeId(UUID.randomUUID())
                .summary("New Issue")
                .projectId(UUID.fromString("1d6846ce-0a75-4a39-a49e-dea0d4be8b25"))
                .pointEstimate(2)
                .issueStatusId(issueStatusId)
                .issueTypeId(issueTypeId)
                .build();

        assertThrows(NoSuchElementException.class, () -> {
            service.createIssue(command);
        });
    }
}
