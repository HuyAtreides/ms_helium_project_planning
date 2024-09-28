package app.helium.projectplanning.test.shared.datetime;

import app.helium.projectplanning.core.application.service.AbstractDateTimeService;
import app.helium.projectplanning.test.shared.constant.CommonTestConstant;
import java.time.Instant;

public class DateTimeServiceWithFixedInstant implements AbstractDateTimeService {

    @Override
    public Instant getCurrentInstant() {
        return CommonTestConstant.FIXED_NOW_INSTANT;
    }
}
