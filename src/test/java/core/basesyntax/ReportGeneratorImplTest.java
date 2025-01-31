package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static final Storage STORAGE = new Storage();
    private final ReportGenerator reportGenerator = new ReportGeneratorImpl(STORAGE);
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
        STORAGE.getInventory().clear();
        STORAGE.getInventory().put("Apple", 20);
        STORAGE.getInventory().put("Mango", 10);
        STORAGE.getInventory().put("Avocado", 15);
    }

    @Test
    public void getReport_validData_Ok() {

        Assertions.assertEquals(expectedReport, reportGenerator.getReport(),
                "Reports are not equal");
    }

    @Test
    public void getReport_emptyStorage_Ok() {
        STORAGE.getInventory().clear();
        String emptyStorageExpected = "fruit,quantity" + System.lineSeparator();
        Assertions.assertEquals(reportGenerator.getReport(), emptyStorageExpected,
                "Reports are not equal");
    }

}
