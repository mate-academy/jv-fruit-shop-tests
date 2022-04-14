package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static String reportName;
    private String expected;
    private String actual;

    @BeforeClass
    public static void beforeClass() throws Exception {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_ok() {
        Storage.getFruitsStorage().put(new Fruit("banana"), 10);
        Storage.getFruitsStorage().put(new Fruit("apple"), 12);
        expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,10"
                + System.lineSeparator()
                + "apple,12"
                + System.lineSeparator();
        actual = reportService.createReport(Storage.getFruitsStorage(), reportName);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.getFruitsStorage().clear();
    }
}
