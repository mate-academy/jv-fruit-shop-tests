package core.basesyntax.tests.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruitsStorage.clear();
    }

    @Test
    public void reportService_CorrectDataOneLine_Ok() {
        Storage.fruitsStorage.put("banana", 20);
        Storage.fruitsStorage.put("apple", 120);
        String actual = reportService.createReport(Storage.fruitsStorage);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,120" + System.lineSeparator();
        assertEquals(actual, expected);
    }

    @Test
    public void reportService_EmptyData_Ok() {
        String actual = reportService.createReport(Storage.fruitsStorage);
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(actual, expected);
    }

    @Test (expected = RuntimeException.class)
    public void reportService_NullData_Ok() {
        reportService.createReport(null);
    }
}
