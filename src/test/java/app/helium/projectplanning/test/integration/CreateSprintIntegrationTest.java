package app.helium.projectplanning.test.integration;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.helium.projectplanning.core.application.command.CreateSprintCommand;
import app.helium.projectplanning.core.application.service.ProjectPlanningService;
import app.helium.projectplanning.core.domain.model.Sprint;
import app.helium.projectplanning.test.shared.constant.CommonTestConstant;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlConfig.TransactionMode;

@IntegrationTest
public class CreateSprintIntegrationTest {

    @Autowired
    private ProjectPlanningService projectPlanningService;

    @Test
    @Sql(
            scripts = {
                    "classpath:/sql_scripts/clear_all_tables.sql",
                    "classpath:/sql_scripts/insert_project.sql"
            },
            config = @SqlConfig(transactionMode = TransactionMode.ISOLATED)
    )
    void create_sprint_with_valid_data_should_success() {
        Sprint sprint = projectPlanningService.createSprint(CreateSprintCommand.builder()
                .projectId(CommonTestConstant.DEFAULT_TEST_PROJECT_ID)
                .goal("Create sprint feature")
                .name("T24 Sprint")
                .dueDate(Instant.parse("2025-05-07T10:29:00Z"))
                .startDate(CommonTestConstant.FIXED_NOW_INSTANT)
                .build()
        );

        assertNotNull(sprint.getId());
    }


    @Test
    @Sql(
            scripts = {
                    "classpath:/sql_scripts/clear_all_tables.sql",
                    "classpath:/sql_scripts/insert_project.sql"
            },
            config = @SqlConfig(transactionMode = TransactionMode.ISOLATED)
    )
    void create_sprint_within_non_exist_project_should_fail() {
        assertThrows(NoSuchElementException.class, () ->
                projectPlanningService.createSprint(CreateSprintCommand.builder()
                        .projectId(UUID.fromString("1d6846ce-0a75-4a39-a49e-dea0d4be8b49"))
                        .goal("Create sprint feature")
                        .name("T24 Sprint")
                        .dueDate(Instant.parse("2025-05-07T10:29:00Z"))
                        .startDate(CommonTestConstant.FIXED_NOW_INSTANT)
                        .build()
                )
        );
    }
}
