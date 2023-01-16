package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import core.basesyntax.storage.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static Fruit banana;
    private static Fruit apple;

    @BeforeClass
    public static void beforeClass() {
        Storage.storage.clear();
        reportService = new ReportServiceImpl();
        apple = new Fruit("apple");
        banana = new Fruit("banana");
    }

    @Test
    public void report_validData_Ok() {
        Storage.storage.put(apple, 33);
        Storage.storage.put(banana, 22);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,22" + System.lineSeparator()
                + "apple,33" + System.lineSeparator();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }
}
