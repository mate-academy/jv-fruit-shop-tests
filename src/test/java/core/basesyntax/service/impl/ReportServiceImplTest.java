package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String HEADER = "fruit,quantity" + System.lineSeparator();
    private static final Map<Fruit, Integer> storage = Storage.getStorage();
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void getReport_OnlyHeader_ok() {
        String actual = reportService.getReport();
        assertEquals(HEADER, actual);
    }

    @Test
    public void getReport_OneFruit_ok() {
        storage.put(new Fruit("banana"), 5);
        String expected = HEADER + "banana,5" + System.lineSeparator();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_TwoFruitsReport_ok() {
        storage.put(new Fruit("banana"), 5);
        storage.put(new Fruit("apple"), 25);
        String expected = HEADER + "banana,5" + System.lineSeparator()
                + "apple,25" + System.lineSeparator();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        storage.clear();
    }
}
