package app.helium.projectplanning.infra.api.rest.mapper;

import app.helium.projectplanning.core.application.command.CreateSprintCommand;
import app.helium.projectplanning.infra.api.rest.request.CreateSprintPayload;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = CommonMapper.class)
public interface CreateSprintPayloadMapper {

    @Mapping(target = "projectId", source = "projectId")
    CreateSprintCommand toCommand(CreateSprintPayload payload, UUID projectId);
}
