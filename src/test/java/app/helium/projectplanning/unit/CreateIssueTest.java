package app.helium.projectplanning.unit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Named.named;

import app.helium.projectplanning.core.domain.IssueStatus;
import app.helium.projectplanning.core.domain.IssueType;
import app.helium.projectplanning.core.domain.Project;
import app.helium.projectplanning.core.domain.factory.IssueFactory;
import app.helium.projectplanning.core.domain.request.CreateIssueRequest;
import app.helium.projectplanning.shared.DomainEntityValidator;
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

    @Test
    void issue_should_be_created_successfully_when_given_valid_data() {

    }

/*    @ParameterizedTest
    @MethodSource("createIssueRequestWithDifferentIssueType")
    void correct_issue_type_should_be_created_correctly_when_given_valid_data() {

    }*/

    private static Stream<Arguments> invalidCreateIssueRequest() {
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

        return Stream.of(
                Arguments.of(
                        named("invalid date range",
                                CreateIssueRequest
                                        .builder()
                                        .project(project)
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .creatorId(UUID.randomUUID())
                                        .summary("New Issue")
                                        .issueName("HE-1104")
                                        .startDate(Instant.parse("2025-09-04T00:00:00Z"))
                                        .dueDate(Instant.parse("2025-04-11T00:00:00Z"))
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
                                        .issueStatusId(issueStatusId)
                                        .issueTypeId(issueTypeId)
                                        .build()
                        )
                ),

                Arguments.of(
                        named("missing creator id",
                                CreateIssueRequest
                                        .builder()
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
        return Stream.of();
    }

    private static Stream<Arguments> createIssueRequestWithDifferentIssueType() {
        return Stream.of();
    }

}
