package io.github.aguther.jira.quarkus.client.api;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.List;

public class IssueFields {

  public String summary;
  public JsonNode description;
  public IssueStatus status;
  public String created;
  public String updated;
  public List<IssueLink> issuelinks;
  public List<String> labels;
  public IssuePriority priority;
  public IssueProject project;

}
