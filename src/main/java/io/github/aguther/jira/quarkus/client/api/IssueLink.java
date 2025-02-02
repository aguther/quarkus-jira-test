package io.github.aguther.jira.quarkus.client.api;

public class IssueLink {

  public String id;
  public IssueLinkType type;
  public IssueLinkInfo inwardIssue;
  public IssueLinkInfo outwardIssue;

}
