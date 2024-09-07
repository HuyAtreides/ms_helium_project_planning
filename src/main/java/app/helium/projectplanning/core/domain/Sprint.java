package app.helium.projectplanning.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@NoArgsConstructor
@Builder
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@Table(name = "sprint", schema = "project_planning")
@AllArgsConstructor
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @Column(name = "goal")
    @JdbcTypeCode(SqlTypes.VARCHAR)

    private String goal;

    @Column(name = "start_date")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    private Instant startDate;

    @Column(name = "due_date")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    private Instant dueDate;

    @Column(name = "project_id")
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID project_id;

    @Column(name = "created_at")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    private Instant createdAt;

    @Column(name = "last_updated_at")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    private Instant lastUpdatedAt;

    @Column(name = "last_updated_by_id")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    private UUID lastUpdatedById;

    public void addIssue(Issue issue) {

    }

    public void removeIssue(Issue issue) {

    }

    public void updateSprint() {

    }
}
