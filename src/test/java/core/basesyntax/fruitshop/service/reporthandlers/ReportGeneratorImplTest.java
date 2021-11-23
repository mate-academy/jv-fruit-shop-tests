package core.basesyntax.fruitshop.service.reporthandlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.fruitshop.fruitstoragedb.FruitStorage;
import core.basesyntax.fruitshop.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private ReportGeneratorImpl reportGenerator;
    private String expected;
    private String actual;

    @Before
    public void setUp() throws Exception {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void emptyReport_generateReport_Ok() {
        expected = "fruit, quantity" + System.lineSeparator();
        actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    public void notEmptyReport_generateReport_Ok() {
        FruitStorage.getStorage().put(new Fruit("Orange"), 500);
        FruitStorage.getStorage().put(new Fruit("Grapes  "
                + System.lineSeparator()), Integer.MAX_VALUE);
        expected = "fruit, quantity" + System.lineSeparator()
                + "Grapes, " + Integer.MAX_VALUE + System.lineSeparator()
                + "Orange, 500" + System.lineSeparator();
        actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() throws Exception {
        FruitStorage.getStorage().clear();
    }
}
