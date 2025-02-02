package io.github.aguther.jira.quarkus.client;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.aguther.jira.quarkus.client.api.Issue;
import io.github.aguther.jira.quarkus.client.api.SearchResult;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestResponse;

@RegisterRestClient(configKey = "jira")
@ClientHeaderParam(name = "Accept", value = "application/json")
@ClientHeaderParam(name = "Content-Type", value = "application/json")
@ClientHeaderParam(name = "Authorization", value = "Basic ${jira.token}")
public interface JiraRestClientApi2 {

  @GET
  @Path("search")
  RestResponse<SearchResult> search(@QueryParam("jql") String jql);

  @GET
  @Path("issue/{issueIdOrKey}")
  RestResponse<Issue> getIssue(String issueIdOrKey);

  @POST
  @Path("issue")
  RestResponse<JsonNode> createIssueFromJson(String bodyJson);
}
