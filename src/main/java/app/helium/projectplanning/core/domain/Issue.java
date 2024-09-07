package app.helium.projectplanning.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Lob;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import java.time.Instant;
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
@AllArgsConstructor
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

    @Column(name = "project_id")
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID project_id;

    public void updateIssue() {

    }

    public void addLabel() {

    }

    public void removeLabel() {

    }
}
