package app.helium.projectplanning.unit;

import static org.junit.jupiter.api.Named.named;

import app.helium.projectplanning.core.domain.request.CreateIssueRequest;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreateIssueTest {

    @ParameterizedTest
    @MethodSource("invalidCreateIssueRequest")
    void issue_should_be_unsuccessfully_created_when_given_invalid_data(
            CreateIssueRequest request
    ) {

    }

    @Test
    void issue_should_be_created_successfully_when_given_valid_data() {

    }

    @Test
    void end_date_should_be_calculated_correctly_when_point_estimate_and_start_date_are_provided() {

    }

    @Test
    void when_calculated_end_date_before_start_date_then_issue_should_not_be_created() {

    }

    @ParameterizedTest
    @MethodSource("createIssueRequestWithDifferentIssueType")
    void correct_issue_type_should_be_created_correctly_when_given_valid_data() {

    }

    private static Stream<Arguments> invalidCreateIssueRequest() {
        return Stream.of(
                Arguments.of(
                        named(
                                "invalid date range",
                                CreateIssueRequest
                                        .builder()
                                        .startDate(Instant.parse("2024-09-04T00:00:00Z"))
                                        .dueDate(Instant.parse("2024-04-11T00:00:00Z"))
                                        .build()
                        )
                ),
                Arguments.of(
                        named(
                                "attachment urls contain invalid url",
                                CreateIssueRequest
                                        .builder()
                                        .attachmentURLs(List.of(
                                                "https://cache.img.com",
                                                "https:/in@valid.com",
                                                "https://invalid. .url"
                                        ))
                                        .build()
                        )
                ),
                Arguments.of(
                        named(
                                "estimate point is negative",
                                CreateIssueRequest
                                        .builder()
                                        .pointEstimate(-1)
                                        .build()
                        )
                ),
                Arguments.of(
                        named(
                                "start + point estimate is smaller than due date",
                                CreateIssueRequest
                                        .builder()
                                        .startDate(Instant.parse("2024-09-04T00:00:00Z"))
                                        .dueDate(Instant.parse("2024-09-13T00:00:00Z"))
                                        .pointEstimate(2)
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
