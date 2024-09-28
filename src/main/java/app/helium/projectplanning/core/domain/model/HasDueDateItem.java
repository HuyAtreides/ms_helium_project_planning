package app.helium.projectplanning.core.domain.model;

import java.time.Instant;

public interface HasDueDateItem {
    Instant getDueDate();
    Instant getCreatedAt();
    default boolean hasValidDueDate() {
        if (getDueDate() == null) {
            return true;
        }

        return getCreatedAt().isBefore(getDueDate());
    }
}
