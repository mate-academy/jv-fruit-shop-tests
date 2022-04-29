package core.basesyntax.service.report;

import static org.junit.Assert.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_validCase_OK() {
        Storage.dataBase.put("banana", 18);
        Storage.dataBase.put("apple", 14);
        Storage.dataBase.put("default", 0);
        String actual = reportService.createReport(Storage.dataBase);
        String expected = "fruit,quantity\n"
                + "banana,18" + System.lineSeparator()
                + "apple,14" + System.lineSeparator()
                + "default,0" + System.lineSeparator();
        assertEquals(expected, actual);
    }

    @After
    public void clearStorage() {
        Storage.dataBase.clear();
    }
}

