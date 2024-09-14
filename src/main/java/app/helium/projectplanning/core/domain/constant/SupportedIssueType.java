package app.helium.projectplanning.core.domain.constant;

import java.util.Arrays;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SupportedIssueType {
    TASK("task"),
    EPIC("epic"),
    USER_STORY("user_story"),
    SUB_TASK("sub_task"),
    CUSTOM("custom"),
    BUG("bug");

    public final String value;

    public static SupportedIssueType from(String stringValue) {
        return Arrays.stream(SupportedIssueType.values())
                .filter(type -> type.value.equals(stringValue))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Unknown default issue type")
                );
    }
}
