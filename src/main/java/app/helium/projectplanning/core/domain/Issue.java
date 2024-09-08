package app.helium.projectplanning.core.domain;

import static app.helium.projectplanning.core.domain.utils.DateRangeUtils.validateDateRange;
import static app.helium.projectplanning.core.domain.utils.URLUtils.validateURLs;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@Entity
@Table(name = "issue", schema = "project_planning")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "summary")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String summary;

    @Lob
    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String description;

    @Column(name = "name", unique = true)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @Column(name = "attachment_urls")
    @JdbcTypeCode(SqlTypes.ARRAY)
    private List<String> attachmentURLs;

    @Column(name = "created_at")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    private Instant createdAt;

    @Column(name = "last_updated_at")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    private Instant lastUpdatedAt;

    @Column(name = "last_updated_by_id")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    private UUID lastUpdatedById;

    @Column(name = "point_estimate")
    @JdbcTypeCode(SqlTypes.INTEGER)
    private Integer pointEstimate;

    @Column(name = "start_date")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    private Instant startDate;

    @Column(name = "due_date")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    private Instant dueDate;

    @Column(name = "assignee_id")
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID assigneeId;

    @Column(name = "reporter_id")
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID reporterId;

    @Column(name = "project_id")
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID projectId;

    @Column(name = "sprint_id")
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID sprintId;

    @ManyToOne
    @JoinColumn(name = "issue_type_id")
    private IssueType issueType;

    @ManyToOne
    @JoinColumn(name = "issue_status_id")
    private IssueStatus issueStatus;

    public Issue(UUID id, String summary, String description, List<String> attachmentURLs,
            Instant createdAt, Instant lastUpdatedAt, UUID lastUpdatedById, Integer pointEstimate,
            Instant startDate, Instant dueDate, UUID assigneeId, UUID reporterId, UUID projectId,
            UUID sprintId) {
        validateDateRange(startDate, dueDate);
        validateURLs(attachmentURLs);
        validateIfDueDateInThePast(dueDate);
        validatePointEstimate(pointEstimate);
        validateDateRangeWithPointEstimate(pointEstimate, startDate, dueDate);
        this.id = id;
        this.summary = summary;
        this.description = description;
        this.attachmentURLs = attachmentURLs;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.lastUpdatedById = lastUpdatedById;
        this.pointEstimate = pointEstimate;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.assigneeId = assigneeId;
        this.reporterId = reporterId;
        this.projectId = projectId;
        this.sprintId = sprintId;
    }

    public void assignToUser(UUID userId) {
        this.assigneeId = userId;
    }

    public void assignToReporter(UUID reporterId) {
        this.reporterId = reporterId;
    }

    public void updateDueDateBaseOnPointEstimateAndStartDate() {
        this.dueDate = calculateDueDateFromPointEstimateAndStartDate();
    }

    private Instant calculateDueDateFromPointEstimateAndStartDate() {
        if (startDate != null && pointEstimate != null) {
            return startDate.plus(pointEstimate, ChronoUnit.DAYS);
        }

        return null;
    }

    private void validateIfDueDateInThePast(Instant dueDate) {
        if (dueDate.isBefore(Instant.now())) {
            throw new IllegalArgumentException("Due date can not be in the past");
        }
    }

    private void validateDateRangeWithPointEstimate(Integer pointEstimate, Instant startDate,
            Instant dueDate) {
        if (pointEstimate != null && startDate != null && dueDate != null) {
            if (startDate.plus(pointEstimate, ChronoUnit.DAYS).isAfter(dueDate)) {
                throw new IllegalArgumentException("Start date + point estimate is after due date");
            }
        }
    }

    private void validatePointEstimate(Integer pointEstimate) {
        if (pointEstimate == null) {
            return;
        }

        if (pointEstimate <= 0) {
            throw new IllegalArgumentException("Point estimate can be smaller or equal to 0");
        }
    }
}
