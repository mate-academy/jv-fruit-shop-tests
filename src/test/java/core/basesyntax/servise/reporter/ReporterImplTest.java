package core.basesyntax.servise.reporter;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReporterImplTest {
    private Reporter reporter;

    @Before
    public void setUp() {
        reporter = new ReporterImpl();
    }

    @Test
    public void createReporter_ok() {
        Storage.data.put(new Fruit("apple"), 20);
        String lineSeparator = System.lineSeparator();
        String expected = "fruit,quantity"
                + lineSeparator
                + "apple"
                + ","
                + "20"
                + lineSeparator;
        String actual = reporter.createReport();
        Assert.assertEquals(expected, actual);
    }
}
