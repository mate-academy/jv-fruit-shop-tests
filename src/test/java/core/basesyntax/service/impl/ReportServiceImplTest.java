package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        String expected = "fruit,quantity";
        String actual = reportService.createReport();
        assertEquals("Invalid report operation ", expected, actual);
    }

    @Test
    public void createReport_validReport_Ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,10" + System.lineSeparator()
                + "apple,15";
        Storage.storage.put(new Fruit("banana"), 10);
        Storage.storage.put(new Fruit("apple"), 15);
        String actual = reportService.createReport();
        assertEquals("Invalid report operation ",expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
