package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeClass
    public static void init() {
        Storage.storage.clear();
        reportService = new ReportServiceImpl();
    }

    @Test
    public void report_validData_ok() {
        Fruit apple = new Fruit("apple");
        Fruit banana = new Fruit("banana");
        Storage.storage.put(banana, 22);
        Storage.storage.put(apple, 33);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,22" + System.lineSeparator()
                + "apple,33" + System.lineSeparator();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }
}
