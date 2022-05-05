package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void reportData_Ok() {
        Storage.storage.put(new Fruit("banana"), 183);
        Storage.storage.put(new Fruit("apple"), 49);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,183" + System.lineSeparator()
                + "apple,49" + System.lineSeparator();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }
}
