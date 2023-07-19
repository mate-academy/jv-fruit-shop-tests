package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.CsvReportService;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CsvReportServiceImplTest {
    private static Map<Fruit, Integer> storage;
    private CsvReportService reportService;

    @Before
    public void setUp() {
        storage = new HashMap<>();
        reportService = new CsvReportServiceImpl();
    }

    @Test
    public void createReport_Ok() {
        storage.put(new Fruit("banana"), 20);
        storage.put(new Fruit("pie"), 5);
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,20"
                + System.lineSeparator() + "pie,5" + System.lineSeparator();
        String actual = reportService.createReport(storage);
        assertEquals(expected,actual);
    }

    @Test
    public void createReport_EmptyStorage_Ok() {
        String actual = reportService.createReport(storage);
        String expected = "No data provided";
        assertEquals(expected, actual);
    }
}
