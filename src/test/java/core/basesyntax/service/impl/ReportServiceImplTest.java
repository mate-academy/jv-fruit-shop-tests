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
    public void report_validData_ok() {
        Fruit banana = new Fruit("banana");
        Fruit apple = new Fruit("apple");
        Storage.storage.put(banana, 22);
        Storage.storage.put(apple, 33);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,22" + System.lineSeparator()
                + "apple,33" + System.lineSeparator();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
