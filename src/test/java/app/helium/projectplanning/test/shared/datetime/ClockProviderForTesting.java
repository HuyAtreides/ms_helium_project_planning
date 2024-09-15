package app.helium.projectplanning.test.shared.datetime;

import jakarta.validation.ClockProvider;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ClockProviderForTesting implements ClockProvider {

    @Override
    public Clock getClock() {
        return Clock.fixed(
                Instant.parse("2025-04-11T00:00:00Z"),
                ZoneId.of("UTC")
        );
    }
}
