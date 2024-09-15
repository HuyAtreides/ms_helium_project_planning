package app.helium.projectplanning.core.application.service;

import app.helium.projectplanning.core.application.command.CreateIssueCommand;
import app.helium.projectplanning.core.application.mapper.CreateIssueRequestCommandMapper;
import app.helium.projectplanning.core.domain.factory.IssueFactory;
import app.helium.projectplanning.core.domain.model.Issue;
import app.helium.projectplanning.core.domain.model.Project;
import app.helium.projectplanning.core.domain.request.CreateIssueRequest;
import app.helium.projectplanning.infra.repository.ProjectRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectPlanningService {

    private final IssueFactory issueFactory;
    private final CreateIssueRequestCommandMapper mapper = Mappers.getMapper(
            CreateIssueRequestCommandMapper.class
    );
    private final ProjectRepository projectRepository;

    @Transactional
    public Issue createIssue(CreateIssueCommand command) {
        UUID projectId = command.getProjectId();
        Project project = projectRepository.findById(projectId).orElseThrow();
        long nextSequence = projectRepository.getNextSequence();
        String issueName = project.generateIssueName(nextSequence);

        CreateIssueRequest request = mapper.mapToCreateIssueRequest(command, issueName, project);
        Issue issue = issueFactory.createIssue(request);
        project.addIssue(issue);
        projectRepository.save(project);

        return project.findIssueByName(issueName);
    }
}
