package app.helium.projectplanning.core.domain.model;

import app.helium.projectplanning.core.domain.request.CreateSprintRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedSubgraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.repository.EntityGraph;


@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@Table(name = "project_read_only", schema = "project_planning")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NamedEntityGraph(name = "project(issues)", attributeNodes = {
        @NamedAttributeNode(value = "issues", subgraph = "project(issues(type, status))"),
        @NamedAttributeNode("issueTypes"),
        @NamedAttributeNode("issueStatuses")
}, subgraphs = {
        @NamedSubgraph(name = "project(issue(type, status))", attributeNodes = {
                @NamedAttributeNode("type"),
                @NamedAttributeNode("status")
        })
})
@NamedEntityGraph(name = "project(sprints)", attributeNodes = {
        @NamedAttributeNode("sprints")
})
@Immutable
public class Project {

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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "project_id", updatable = false)
    @Default
    private Set<Issue> issues = new LinkedHashSet<>();

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "project_id", updatable = false)
    @Default
    private Set<Sprint> sprints = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "project_id", updatable = false)
    @Default
    private Set<IssueType> issueTypes = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "project_id", updatable = false)
    @Default
    private Set<IssueStatus> issueStatuses = new LinkedHashSet<>();

    public void addIssue(Issue issue) {
        issues.add(issue);
    }

    //TODO: implement after create_sprint feature
    public void addIssue(Issue issue, UUID sprintId) {

    }

    public Sprint createNewSprint(CreateSprintRequest request) {
        Sprint sprint = Sprint.builder()
                .id(request.getId())
                .name(request.getName())
                .dateRange(DateRange.from(request.getStartDate(), request.getDueDate()))
                .lastUpdatedAt(request.getCreatedAt())
                .createdAt(request.getCreatedAt())
                .lastUpdatedById(request.getCreatorId())
                .creatorId(request.getCreatorId())
                .projectId(this.id)
                .goal(request.getGoal())
                .build();

        sprints.add(sprint);

        return sprint;
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

    private Issue findIssueById(UUID id) {
        return issues.stream()
                .filter(issue -> issue.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }
}
