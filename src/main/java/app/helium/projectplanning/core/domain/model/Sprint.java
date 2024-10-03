package app.helium.projectplanning.core.domain.model;

import app.helium.projectplanning.core.domain.validation.constraint.ValidDateRange;
import app.helium.projectplanning.core.domain.validation.constraint.ValidDueDate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@Table(name = "sprint", schema = "project_planning")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@ValidDueDate
public class Sprint implements HasDueDateItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    @NotNull
    @Getter(AccessLevel.PUBLIC)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Default
    @NotNull
    @Getter(AccessLevel.PUBLIC)
    private String name = "New Sprint";

    @Column(name = "goal")
    @Getter(AccessLevel.PUBLIC)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String goal;

    @Embedded
    @ValidDateRange
    @Valid
    @Getter(AccessLevel.PUBLIC)
    private DateRange dateRange;

    @Column(name = "project_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @NotNull
    private UUID projectId;

    @Column(name = "created_at")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    @NotNull
    @Getter(AccessLevel.PUBLIC)
    private Instant createdAt;

    @Column(name = "last_updated_at")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    @NotNull
    private Instant lastUpdatedAt;

    @Column(name = "last_updated_by_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @NotNull
    private UUID lastUpdatedById;

    @Override
    public Instant getDueDate() {
        return dateRange.getEndDate();
    }

    @Column(name = "creator_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @NotNull
    private UUID creatorId;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sprint_id")
    @Default
    @Getter(AccessLevel.PUBLIC)
    private Set<Issue> issues = new LinkedHashSet<>();

    //TODO: implement after create sprint feature
    void addIssue(Issue issue) {

    }
}
