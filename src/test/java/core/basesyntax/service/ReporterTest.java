package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.FruitReportService;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReporterTest {
    private final ReportService reportService = new FruitReportService();

    @BeforeClass
    public static void beforeClass() {
        Storage.storage.clear();
    }

    @Test
    public void test_Report_OK() {
        Storage.storage.put(new Fruit("banana"), 12);
        Storage.storage.put(new Fruit("apple"), 34);
        Storage.storage.put(new Fruit("orange"), 1);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana," + 12 + System.lineSeparator()
                + "apple," + 34 + System.lineSeparator()
                + "orange," + 1;
        String actual = reportService.makeReport();
        assertEquals(expected, actual);
    }
}
