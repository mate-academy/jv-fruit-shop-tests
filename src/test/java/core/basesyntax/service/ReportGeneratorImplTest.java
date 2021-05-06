package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static final String TITLE = "fruit,quantity\n";
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @Before
    public void clearStorage() {
        Storage.getFruitStorage().clear();
    }

    @Test
    public void generateReport_emptyStorage_ok() {
        String actual = reportGenerator.generateReport();
        assertEquals(TITLE, actual);
    }

    @Test
    public void generateReport_filledStorage_ok() {
        Storage.getFruitStorage().put("paprika", 10);
        Storage.getFruitStorage().put("melon", 3);
        String actual = reportGenerator.generateReport();
        String expected = TITLE
                + "paprika" + "," + 10
                + System.lineSeparator()
                + "melon" + "," + 3
                + System.lineSeparator();
        assertEquals(expected, actual);
    }
}
