package app.helium.projectplanning.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.FutureOrPresent;
import java.time.Instant;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Embeddable
@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
public class DateRange {
    @Column(name = "start_date")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    @EqualsAndHashCode.Include
    private Instant startDate;

    @Column(name = "due_date")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    @FutureOrPresent
    @EqualsAndHashCode.Include
    private Instant endDate;

    public static DateRange from(Instant startDate, Instant endDate) {
        return new DateRange(startDate, endDate);
    }
}
