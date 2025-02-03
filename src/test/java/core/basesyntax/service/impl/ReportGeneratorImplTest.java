package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportGeneratorImplTest {
    private static final Storage storage = new Storage();
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl(storage);
    private final String expectedReport = "fruit,quantity"
            + System.lineSeparator()
            + "Apple,20"
            + System.lineSeparator()
            + "Mango,10"
            + System.lineSeparator()
            + "Avocado,15"
            + System.lineSeparator();

    @BeforeEach
    public void setUp() {
        storage.getInventory().clear();
        storage.getInventory().put("Apple", 20);
        storage.getInventory().put("Mango", 10);
        storage.getInventory().put("Avocado", 15);
    }

    @Test
    public void getReport_validData_Ok() {
        assertEquals(expectedReport, reportGenerator.getReport(),
                "Reports are not equal");
    }

    @Test
    public void getReport_emptyStorage_Ok() {
        storage.getInventory().clear();
        String emptyStorageExpected = "fruit,quantity" + System.lineSeparator();
        assertEquals(reportGenerator.getReport(), emptyStorageExpected,
                "Reports are not equal");
    }
}
