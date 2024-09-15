package app.helium.projectplanning.core.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
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
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.domain.Persistable;

@Entity
@Builder
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@Table(name = "project_read_only", schema = "project_planning")
@AllArgsConstructor
@Immutable
public class Project implements Persistable<UUID> {

    @Id
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    @EqualsAndHashCode.Include
    @Getter(AccessLevel.PUBLIC)
    private UUID id;

    @Column(name = "project_lead_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @Getter(AccessLevel.PUBLIC)
    private UUID projectLeadId;

    @Column(name = "key", nullable = false, length = 7)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String key;

    @Column(name = "default_assignee_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @Getter(AccessLevel.PUBLIC)
    private UUID defaultAssigneeId;

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    @Default
    private Set<Issue> issues = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    @Default
    private Set<Sprint> sprints = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    @Default
    private Set<IssueType> issueTypes = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "project_id")
    @Default
    private Set<IssueStatus> issueStatuses = new LinkedHashSet<>();

    @Transient
    private boolean newEntityAddedToProject;

    public void addIssue(Issue issue) {
        issues.add(issue);
        setNewEntityAddedToProject(true);
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

    public IssueStatus getIssueStatusById(UUID id) {
        return issueStatuses.stream()
                .filter(issueStatus -> issueStatus.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Status type with " + id + " not found"));
    }

    public String generateIssueName(long sequence) {
        return key + "-" + sequence;
    }

    public Issue findIssueByName(String name) {
        return issues.stream()
                .filter(issue -> issue.getName().equals(name))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public boolean isNew() {
        return newEntityAddedToProject;
    }
}
