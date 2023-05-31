package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void reportService_validData_ok() {
        Storage.storage.put(new Fruit("banana"), 115);
        Storage.storage.put(new Fruit("apple"), 110);
        Set<Map.Entry<Fruit, Integer>> entries = Storage.storage.entrySet();
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,115" + System.lineSeparator()
                + "apple,110" + System.lineSeparator();
        String actual = reportService.createReport(entries);
        assertEquals(expected, actual);
    }

    @Test
    public void reportService_emptyData_ok() {
        Set<Map.Entry<Fruit, Integer>> entries = Storage.storage.entrySet();
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportService.createReport(entries);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
