package app.helium.projectplanning.infra.api.rest.mapper;

import app.helium.projectplanning.core.domain.model.Sprint;
import app.helium.projectplanning.infra.api.rest.response.SprintPayload;
import org.mapstruct.Mapper;

@Mapper(uses = {CommonMapper.class, IssuePayloadMapper.class})
public interface SprintPayloadMapper {
    SprintPayload mapToSprintPayload(Sprint sprint);
}
