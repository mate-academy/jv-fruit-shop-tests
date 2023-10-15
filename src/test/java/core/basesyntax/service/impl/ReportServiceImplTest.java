package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.Test;

public class ReportServiceImplTest {
    private Storage storage = new Storage();
    private ReportService reportService = new ReportServiceImpl(storage);
    private Fruit apple = new Fruit("apple");
    private Fruit mango = new Fruit("mango");

    @Test
    public void createReport_ok() {
        storage.setFruitQuantity(apple, 10);
        storage.setFruitQuantity(mango, 5);

        String report = reportService.createReport();
        String expectedReport = "fruit,quantity"
                + System.lineSeparator() + "apple,10"
                + System.lineSeparator() + "mango,5"
                + System.lineSeparator();

        assertEquals(expectedReport, report);
    }

    @Test
    public void createReportForEmptyStorage() {
        String report = reportService.createReport();
        String expectedReport = "fruit,quantity" + System.lineSeparator();

        assertEquals(expectedReport, report);
    }

    @Test
    public void createReportForNotExistingStorage() {
        reportService = new ReportServiceImpl(null);
        assertThrows(NullPointerException.class, () -> reportService.createReport());
    }
}
