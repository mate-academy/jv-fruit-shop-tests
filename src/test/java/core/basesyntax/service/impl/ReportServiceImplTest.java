package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String REPORT_TITLE_ROW = "fruit,quantity";
    private static ReportService reportService;

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void report_emptyDataExpectTitleRow_ok() {
        String expected = REPORT_TITLE_ROW;
        String actual = reportService.newReport();
        assertEquals(expected, actual);
    }

    @Test
    public void report_presentData_ok() {
        Storage.storage.put("banana", 152);
        Storage.storage.put("apple", 90);
        String expected = REPORT_TITLE_ROW + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90";
        String actual = reportService.newReport();
        assertEquals(expected, actual);
    }
}
