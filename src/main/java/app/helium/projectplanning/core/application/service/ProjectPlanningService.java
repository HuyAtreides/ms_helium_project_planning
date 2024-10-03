package app.helium.projectplanning.core.application.service;

import app.helium.projectplanning.core.application.command.CreateIssueCommand;
import app.helium.projectplanning.core.application.command.CreateSprintCommand;
import app.helium.projectplanning.core.application.mapper.CreateIssueRequestCommandMapper;
import app.helium.projectplanning.core.application.mapper.CreateSprintRequestMapper;
import app.helium.projectplanning.core.domain.factory.IssueFactory;
import app.helium.projectplanning.core.domain.model.Issue;
import app.helium.projectplanning.core.domain.model.Project;
import app.helium.projectplanning.core.domain.model.Sprint;
import app.helium.projectplanning.core.domain.request.CreateIssueRequest;
import app.helium.projectplanning.core.domain.request.CreateSprintRequest;
import app.helium.projectplanning.infra.repository.ProjectRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectPlanningService {

    private final IssueFactory issueFactory;
    private final CreateIssueRequestCommandMapper mapper = Mappers.getMapper(
            CreateIssueRequestCommandMapper.class
    );
    private final CreateSprintRequestMapper createSprintRequestMapper = Mappers.getMapper(
        CreateSprintRequestMapper.class
    );
    private final AbstractDateTimeService dateTimeService;
    private final ProjectRepository projectRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Issue createIssue(CreateIssueCommand command) {
        log.info("message = create new issue, request = {}", command);
        UUID projectId = command.getProjectId();
        Project project = projectRepository.findById(projectId).orElseThrow();
        long nextSequence = projectRepository.getNextSequence();
        String issueName = project.generateIssueName(nextSequence);

        //TODO: retrieve user id from security context
        UUID creatorId = UUID.fromString("e0dfb21f-1ad9-42eb-94a3-98383ffa6618");
        Instant now = dateTimeService.getCurrentInstant();

        CreateIssueRequest request = mapper.mapToCreateIssueRequest(command, creatorId, issueName, project, now);
        Issue issue = issueFactory.createIssue(request);
        project.addIssue(issue);
        projectRepository.save(project);

        return issue;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Sprint createSprint(CreateSprintCommand command) {
        log.info("message = create new sprint, request = {}", command);
        //TODO: retrieve user id from security context
        UUID creatorId = UUID.fromString("e0dfb21f-1ad9-42eb-94a3-98383ffa6618");
        Project project = projectRepository.findById(command.getProjectId()).orElseThrow();
        Instant now = dateTimeService.getCurrentInstant();
        UUID id = UUID.randomUUID();
        CreateSprintRequest request = createSprintRequestMapper.mapToCreateSprintRequest(id, command, creatorId, now);
        Sprint newSprint = project.createNewSprint(request);
        projectRepository.save(project);

        return newSprint;
    }
}
