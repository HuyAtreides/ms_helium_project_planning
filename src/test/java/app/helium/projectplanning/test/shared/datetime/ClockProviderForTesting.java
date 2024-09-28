package app.helium.projectplanning.test.shared.datetime;

import app.helium.projectplanning.test.shared.constant.CommonTestConstant;
import jakarta.validation.ClockProvider;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class ClockProviderForTesting implements ClockProvider {

    @Override
    public Clock getClock() {
        return Clock.fixed(
                CommonTestConstant.FIXED_NOW_INSTANT,
                ZoneId.of("UTC")
        );
    }
}
