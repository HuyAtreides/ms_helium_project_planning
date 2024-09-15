package app.helium.projectplanning.infra.repository;

import app.helium.projectplanning.core.domain.model.Project;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, UUID> {

    @Query(nativeQuery = true, value = """
            select nextval('project_planning.issue_sequence')
            """)
    long getNextSequence();
}
