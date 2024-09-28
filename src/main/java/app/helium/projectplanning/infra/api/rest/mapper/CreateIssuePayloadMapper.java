package app.helium.projectplanning.infra.api.rest.mapper;

import app.helium.projectplanning.core.application.command.CreateIssueCommand;
import app.helium.projectplanning.infra.api.rest.request.CreateIssuePayload;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CommonMapper.class)
public interface CreateIssuePayloadMapper {
    @Mapping(target = ".", source = "payload")
    @Mapping(target = "projectId", source = "projectId")
    CreateIssueCommand toCommand(CreateIssuePayload payload, UUID projectId);
}
