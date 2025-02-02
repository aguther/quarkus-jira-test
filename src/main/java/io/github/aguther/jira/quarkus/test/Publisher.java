package io.github.aguther.jira.quarkus.test;

import io.vertx.core.eventbus.EventBus;
import jakarta.inject.Singleton;

@Singleton
public class Publisher {

  private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(
      Publisher.class
  );

  EventBus eventBus;

  public Publisher(
      EventBus eventBus
  ) {
    this.eventBus = eventBus;
  }

  public void publish(String message) {
    eventBus.publish("test", message);
  }

}
