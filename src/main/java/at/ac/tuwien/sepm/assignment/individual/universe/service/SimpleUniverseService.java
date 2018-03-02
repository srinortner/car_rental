package at.ac.tuwien.sepm.assignment.individual.universe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

public class SimpleUniverseService implements UniverseService {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final int SLEEP_SECONDS = 2;

    @Override
    public String calculateAnswer() {
        LOG.debug("called calculateAnswer");
        // sleep to simulate heavy load
        try {
            LOG.trace("Going to sleep for {} seconds", SLEEP_SECONDS);
            Thread.sleep(Duration.of(SLEEP_SECONDS, SECONDS).toMillis());
        } catch (InterruptedException e) {
            LOG.warn("Failed to sleep cause {}", e.getMessage());
        }
        return "42!";
    }

}
