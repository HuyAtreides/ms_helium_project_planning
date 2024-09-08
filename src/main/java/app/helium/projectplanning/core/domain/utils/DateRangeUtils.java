package app.helium.projectplanning.core.domain.utils;

import java.time.Instant;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateRangeUtils {

    static public void validateDateRange(Instant startDate, Instant endDate) {
        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date can not be before start date");
        }
    }
}
