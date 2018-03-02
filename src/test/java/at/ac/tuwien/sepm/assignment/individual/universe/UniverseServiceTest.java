package at.ac.tuwien.sepm.assignment.individual.universe;

import at.ac.tuwien.sepm.assignment.individual.universe.service.SimpleUniverseService;
import at.ac.tuwien.sepm.assignment.individual.universe.service.UniverseService;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class UniverseServiceTest {

    private final UniverseService universeService = new SimpleUniverseService();

    @Test
    public void testSimpleUniverseService() {
        Assert.assertThat(universeService.calculateAnswer(), is("42!"));
    }

}
