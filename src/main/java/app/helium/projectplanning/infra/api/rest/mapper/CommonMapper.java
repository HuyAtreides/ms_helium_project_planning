package app.helium.projectplanning.infra.api.rest.mapper;

import app.helium.projectplanning.core.domain.model.DateRange;
import app.helium.projectplanning.infra.api.rest.response.DateRangePayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommonMapper {

    @Mapping(target = "startDate", source = "dateRange.startDate")
    @Mapping(target = "dueDate", source = "dateRange.endDate")
    DateRangePayload mapToDateRangePayload(DateRange dateRange);
}
