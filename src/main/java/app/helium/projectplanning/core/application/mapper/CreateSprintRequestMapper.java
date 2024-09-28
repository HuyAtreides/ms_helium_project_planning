package app.helium.projectplanning.core.application.mapper;

import app.helium.projectplanning.core.application.command.CreateSprintCommand;
import app.helium.projectplanning.core.domain.request.CreateSprintRequest;
import java.time.Instant;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CreateSprintRequestMapper {


    @Mapping(target = "creatorId", source = "creatorId")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "id", source = "id")
    CreateSprintRequest mapToCreateSprintRequest(UUID id, CreateSprintCommand command, UUID creatorId, Instant createdAt);
}
