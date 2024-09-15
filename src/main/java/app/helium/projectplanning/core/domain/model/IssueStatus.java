package app.helium.projectplanning.core.domain.model;


import app.helium.projectplanning.core.domain.constant.SupportedIssueStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
@Entity
@Table(name = "issue_status", schema = "project_planning")
public class IssueStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @JdbcTypeCode(SqlTypes.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "name", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @NotBlank
    @Getter(AccessLevel.PUBLIC)
    private String name;

    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Getter(AccessLevel.PUBLIC)
    private String description;

    @Column(name = "custom", nullable = false)
    @JdbcTypeCode(SqlTypes.BOOLEAN)
    private boolean custom;

    @Column(name = "project_id")
    @JdbcTypeCode(SqlTypes.UUID)
    @NotNull
    private UUID projectId;

    public SupportedIssueStatus getSupportedStatus() {
        if (custom) {
            return SupportedIssueStatus.CUSTOM;
        }

        return SupportedIssueStatus.from(name);
    }
}
