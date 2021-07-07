package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.FruitReportService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReporterTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new FruitReportService();
    }

    @Before
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void report_validData_OK() {
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
