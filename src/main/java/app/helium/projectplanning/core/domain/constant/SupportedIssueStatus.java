package app.helium.projectplanning.core.domain.constant;

import java.util.Arrays;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SupportedIssueStatus {
    TO_DO("to_do"), IN_PROGRESS("in_progress"), CUSTOM("custom"), DONE("done");

    public final String value;


    public static SupportedIssueStatus from(String stringValue) {
        return Arrays.stream(SupportedIssueStatus.values())
                .filter(type -> type.value.equals(stringValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown default issue status"));
    }
}
