package app.helium.projectplanning.infra.api.rest.mapper;

import app.helium.projectplanning.core.domain.model.Issue;
import app.helium.projectplanning.core.domain.model.IssueStatus;
import app.helium.projectplanning.core.domain.model.IssueType;
import app.helium.projectplanning.infra.api.rest.response.IssuePayload;
import app.helium.projectplanning.infra.api.rest.response.IssueStatusPayload;
import app.helium.projectplanning.infra.api.rest.response.IssueTypePayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface IssuePayloadMapper {

    @Mapping(target = "dueDate", source = "dateRange.endDate")
    @Mapping(target = "startDate", source = "dateRange.startDate")
    IssuePayload fromIssueModel(Issue issue);

    IssueStatusPayload fromIssueStatusModel(IssueStatus issueType);

    IssueTypePayload fromIssueTypeModel(IssueType issueType);
}
