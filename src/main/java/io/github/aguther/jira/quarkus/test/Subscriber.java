package io.github.aguther.jira.quarkus.test;

import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.common.annotation.Blocking;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Subscriber {

  private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(
      Subscriber.class
  );

  @Blocking
  @ConsumeEvent("test")
  public void consume(String name) {
    LOGGER.atInfo().log("Received message: '{}'", name);
  }

}
