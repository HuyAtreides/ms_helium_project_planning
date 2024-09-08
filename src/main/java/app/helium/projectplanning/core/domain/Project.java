package app.helium.projectplanning.core.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;
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
@Builder
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@Table(name = "project_read_only", schema = "project_planning")
@AllArgsConstructor
public class Project {

    @Id
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "project_lead_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @Getter(AccessLevel.PUBLIC)
    private UUID projectLeadId;

    @Column(name = "default_assignee_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @Getter(AccessLevel.PUBLIC)
    private UUID defaultAssigneeId;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private Set<Issue> issues = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private Set<Sprint> sprints = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private Set<IssueType> issueTypes = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    private Set<IssueStatus> issueStatuses = new LinkedHashSet<>();

    public void addIssue(Issue issue) {
        issues.add(issue);
        issue.setProjectId(this.id);
    }

    //TODO: implement after create_sprint feature
    public void addIssue(Issue issue, UUID sprintId) {

    }

    private Sprint getSprintById(UUID id) {
        return sprints.stream()
                .filter(sprint -> sprint.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Sprint with " + id + " not found"));
    }

    public IssueType getIssueTypeById(UUID id) {
        return issueTypes.stream()
                .filter(issueType -> issueType.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Issue type with " + id + " not found"));
    }

    public IssueStatus getIssueStatusById(UUID issueStatusId) {
        return issueStatuses.stream()
                .filter(issueStatus -> issueStatus.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Status type with " + id + " not found"));
    }
}
