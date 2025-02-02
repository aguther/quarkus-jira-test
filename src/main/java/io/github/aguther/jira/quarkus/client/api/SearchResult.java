package io.github.aguther.jira.quarkus.client.api;

import java.util.List;

public class SearchResult {

  public String startAt;
  public String maxResults;
  public String total;
  public List<Issue> issues;

}
