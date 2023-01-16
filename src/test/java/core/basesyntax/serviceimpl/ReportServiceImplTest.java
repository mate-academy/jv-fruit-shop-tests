package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportServiceImpl reportService;
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
        Storage.storage.put(banana, 22);
        Storage.storage.put(apple, 33);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "apple,33" + System.lineSeparator()
                + "banana,22" + System.lineSeparator();
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }
}
