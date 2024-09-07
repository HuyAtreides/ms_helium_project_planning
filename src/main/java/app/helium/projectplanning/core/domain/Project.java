package app.helium.projectplanning.core.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private UUID projectLeadId;

    @Column(name = "default_assignee_id")
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID defaultAssigneeId;

    public void addIssue(Issue issue) {

    }

    public void addIssue(Issue issue, UUID sprintId) {

    }

    public void createSprint(Sprint sprint) {

    }

    public void removeSprint() {

    }

    public void removeIssue() {

    }

    public void removeIssueFromSprint() {

    }

    public void updateIssue() {

    }

    public void addLabel() {

    }

    public void removeLabel() {

    }

    private Sprint getSprintById(UUID id) {
        return null;
    }
}
