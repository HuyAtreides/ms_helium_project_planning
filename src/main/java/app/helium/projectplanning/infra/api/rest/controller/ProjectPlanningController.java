package app.helium.projectplanning.infra.api.rest.controller;

import app.helium.projectplanning.core.application.service.ProjectPlanningService;
import app.helium.projectplanning.core.domain.model.Issue;
import app.helium.projectplanning.infra.api.rest.mapper.CreateIssuePayloadMapper;
import app.helium.projectplanning.infra.api.rest.mapper.IssuePayloadMapper;
import app.helium.projectplanning.infra.api.rest.request.CreateIssuePayload;
import app.helium.projectplanning.infra.api.rest.response.IssuePayload;
import java.time.Clock;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProjectPlanningController {
    private final ProjectPlanningService projectPlanningService;
    private final CreateIssuePayloadMapper mapper = Mappers.getMapper(CreateIssuePayloadMapper.class);
    private final IssuePayloadMapper issuePayloadMapper = Mappers.getMapper(IssuePayloadMapper.class);

    @PostMapping("/projects/{project_id}/create/issue")
    public IssuePayload helloWorld(
            @PathVariable(name = "project_id") UUID projectId,
            @RequestBody CreateIssuePayload payload
    ) {

        Issue issue = projectPlanningService.createIssue(mapper.toCommand(payload, projectId));

        return issuePayloadMapper.fromIssueModel(issue);
    }
}
