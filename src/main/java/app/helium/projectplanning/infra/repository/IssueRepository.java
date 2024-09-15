package app.helium.projectplanning.infra.repository;

import app.helium.projectplanning.core.domain.model.Issue;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepository extends CrudRepository<Issue, UUID> {

}
