package app.helium.projectplanning.core.application.mapper;

import app.helium.projectplanning.core.application.command.CreateIssueCommand;
import app.helium.projectplanning.core.domain.model.Project;
import app.helium.projectplanning.core.domain.request.CreateIssueRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CreateIssueRequestCommandMapper {

    @Mapping(target = ".", source = "command")
    @Mapping(target = "issueName", source = "issueName")
    @Mapping(target = "project", source = "project")
    CreateIssueRequest mapToCreateIssueRequest(CreateIssueCommand command, String issueName, Project project);
}
