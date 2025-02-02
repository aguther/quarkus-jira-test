package io.github.aguther.jira.quarkus;

import io.github.aguther.jira.quarkus.client.JiraRestClientApi2;
import io.github.aguther.jira.quarkus.test.Publisher;
import jakarta.ws.rs.core.Response.Status.Family;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "jira",
    mixinStandardHelpOptions = true
)
public class JiraTestCommand implements Runnable {

  private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(
      JiraTestCommand.class
  );

  @Option(names = {"-q", "--query"}, description = "Jira JQL query")
  String jqlQuery;

  private final JiraRestClientApi2 restClientApi2;
  private final Publisher publisher;

  public JiraTestCommand(
      @RestClient JiraRestClientApi2 restClientApi2,
      Publisher publisher
  ) {
    this.restClientApi2 = restClientApi2;
    this.publisher = publisher;
  }

  @Override
  public void run() {
    // execution is starting
    LOGGER.atInfo().log("Starting JiraTestCommand");

    // get application configuration
    var applicationConfig = ConfigProvider.getConfig();

    // get config for the Jira URI
    var baseUri = applicationConfig.getConfigValue("quarkus.rest-client.jira.uri");
    if (baseUri == null || baseUri.getValue() == null || baseUri.getValue().isEmpty()) {
      LOGGER.atError().log("Jira URI not found in configuration");
      return;
    }
    LOGGER.atInfo().log("BaseUri='{}'", baseUri.getValue());

    // publish a message on the event bus for testing
    publisher.publish("Hello from JiraTestCommand");

    // execute a JQL query based on the supplied command line parameter
    try (var searchResult = restClientApi2.search(jqlQuery)) {
      if (Family.SUCCESSFUL == searchResult.toResponse().getStatusInfo().getFamily()) {
        var result = searchResult.getEntity();
        LOGGER.atInfo().log("'{}' issues found", result.total);
      } else {
        LOGGER.atError().log("Search failed: '{}'", searchResult.getStatus());
      }
    }

    // get a specific issue
    try (var searchResult = restClientApi2.getIssue("CPG-3")) {
      if (Family.SUCCESSFUL == searchResult.toResponse().getStatusInfo().getFamily()) {
        var result = searchResult.getEntity();
        LOGGER.atInfo().log("Summary of issue: '{}'", result.fields.summary);
      } else {
        LOGGER.atError().log("Failed to get issue: '{}'", searchResult.getStatus());
      }
    }

    // execution is finished
    LOGGER.atInfo().log("JiraTestCommand finished");
  }

}
