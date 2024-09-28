package app.helium.projectplanning.core.domain.model;

import app.helium.projectplanning.core.domain.validation.constraint.ValidDateRange;
import app.helium.projectplanning.core.domain.validation.constraint.ValidDueDate;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.URL;

@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@Entity
@Table(name = "issue", schema = "project_planning")
@AllArgsConstructor
@ToString
@ValidDueDate
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Issue implements HasDueDateItem {
    @Id
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    @EqualsAndHashCode.Include
    @NotNull
    @Getter(AccessLevel.PUBLIC)
    private UUID id;

    @Column(name = "summary")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @NotBlank
    @Getter(AccessLevel.PUBLIC)
    private String summary;

    @Lob
    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Getter(AccessLevel.PUBLIC)
    private String description;

    @Column(name = "name", unique = true)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @NotBlank
    @Getter(AccessLevel.PUBLIC)
    private String name;

    @Column(name = "attachment_urls")
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Getter(AccessLevel.PUBLIC)
    @Builder.Default
    private List<@URL(message = "URL is malformed", protocol = "https", host = "media.helium.com") String> attachmentURLs = new ArrayList<>();

    @Column(name = "created_at")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    @NotNull
    @Getter(AccessLevel.PUBLIC)
    private Instant createdAt;

    @Column(name = "last_updated_at")
    @JdbcTypeCode(SqlTypes.TIMESTAMP_UTC)
    @NotNull
    @Getter(AccessLevel.PUBLIC)
    private Instant lastUpdatedAt;

    @Column(name = "last_updated_by_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @NotNull
    @Getter(AccessLevel.PUBLIC)
    private UUID lastUpdatedById;

    @Column(name = "point_estimate")
    @JdbcTypeCode(SqlTypes.INTEGER)
    @Positive
    @Getter(AccessLevel.PUBLIC)
    private Integer pointEstimate;

    @Column(name = "assignee_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @Getter(AccessLevel.PUBLIC)
    private UUID assigneeId;

    @Column(name = "reporter_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @Getter(AccessLevel.PUBLIC)
    private UUID reporterId;

    @Column(name = "project_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @NotNull
    @Getter(AccessLevel.PUBLIC)
    private UUID projectId;

    @Column(name = "creator_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @NotNull
    @Getter(AccessLevel.PUBLIC)
    private UUID creatorId;

    @Column(name = "sprint_id")
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID sprintId;

    @ManyToOne
    @JoinColumn(name = "issue_type_id")
    @Getter(AccessLevel.PUBLIC)
    private IssueType type;

    @ManyToOne
    @JoinColumn(name = "issue_status_id")
    @Getter(AccessLevel.PUBLIC)
    private IssueStatus status;

    @Embedded
    @ValidDateRange
    @Valid
    @Getter(AccessLevel.PUBLIC)
    private DateRange dateRange;

    @Override
    public Instant getDueDate() {
        return dateRange.getEndDate();
    }

}
