package app.helium.projectplanning.test.shared.constant;

import java.time.Instant;
import java.util.UUID;

public class CommonTestConstant {
    public static final UUID DEFAULT_USER_ID = UUID.fromString("37584e0e-fd12-490e-ad96-6bf9c980f4e6");
    public static final UUID DEFAULT_TEST_PROJECT_ID = UUID.fromString("1d6846ce-0a75-4a39-a49e-dea0d4be8b40");
    public static final Instant FIXED_NOW_INSTANT = Instant.parse("2025-04-11T00:00:00Z");
}
