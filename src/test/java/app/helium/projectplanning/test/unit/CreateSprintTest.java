package app.helium.projectplanning.test.unit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Named.named;

import app.helium.projectplanning.core.domain.model.Project;
import app.helium.projectplanning.core.domain.request.CreateSprintRequest;
import app.helium.projectplanning.test.shared.constant.CommonTestConstant;
import app.helium.projectplanning.test.shared.validator.DomainEntityValidator;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateSprintTest {

    private final Project project = Project.builder().id(CommonTestConstant.DEFAULT_TEST_PROJECT_ID)
            .build();

    @ParameterizedTest
    @MethodSource("invalidSprintCreate")
    void create_invalid_sprint_should_fail(CreateSprintRequest request) {
        assertThrows(ConstraintViolationException.class, () -> DomainEntityValidator.validate(
                        project.createNewSprint(request)
                )
        );
    }

    @Test
    void create_valid_sprint_should_success() {
        assertDoesNotThrow(() -> DomainEntityValidator.validate(
                project.createNewSprint(
                        CreateSprintRequest
                                .builder()
                                .goal("Finish create sprint feature")
                                .name("Sprint 2")
                                .startDate(Instant.parse("2025-04-11T00:00:00Z"))
                                .dueDate(Instant.parse("2025-04-19T00:00:00Z"))
                                .createdAt(Instant.now())
                                .creatorId(UUID.randomUUID())
                                .id(UUID.randomUUID())
                                .build()
                )
        ));
    }

    private static Stream<Arguments> invalidSprintCreate() {
        return Stream.of(
                Arguments.of(named("sprint missing id", CreateSprintRequest
                                .builder()
                                .createdAt(Instant.now())
                                .creatorId(UUID.randomUUID())
                                .build()
                        )
                ),
                Arguments.of(named("sprint missing created At", CreateSprintRequest
                                .builder()
                                .id(UUID.randomUUID())
                                .creatorId(UUID.randomUUID())
                                .build()
                        )
                ),
                Arguments.of(named("sprint missing creator id", CreateSprintRequest
                                .builder()
                                .createdAt(Instant.now())
                                .id(UUID.randomUUID())
                                .build()
                        )
                ),
                Arguments.of(named("sprint with invalid date range", CreateSprintRequest
                                .builder()
                                .startDate(Instant.parse("2025-09-04T00:00:00Z"))
                                .dueDate(Instant.parse("2025-04-11T00:00:00Z"))
                                .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                .creatorId(UUID.randomUUID())
                                .id(UUID.randomUUID())
                                .build()
                        )
                ),
                Arguments.of(named("sprint with due date in the past", CreateSprintRequest
                                .builder()
                                .startDate(Instant.parse("2025-03-18T00:00:00Z"))
                                .dueDate(Instant.parse("2025-04-01T00:00:00Z"))
                                .creatorId(UUID.randomUUID())
                                .createdAt(CommonTestConstant.FIXED_NOW_INSTANT)
                                .id(UUID.randomUUID())
                                .build()
                        )
                )
        );
    }
}
