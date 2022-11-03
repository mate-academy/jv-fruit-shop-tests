package core.basesyntax;

import static org.junit.Assert.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static Storage storage;

    private static final String VALID_REPORT = "fruit,quantity \n"
            + "banana,25\r\n"
            + "apple,25\r\n";
    private static final String VALID_REPORT_EMPTY = "fruit,quantity \n";

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
        storage = new Storage();
        storage.storage.put("banana", 25);
        storage.storage.put("apple", 25);
    }

    @Test
    public void generateReport_Ok() {
        String expected = VALID_REPORT;
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    public void generateReportEmptyStorage_Ok() {
        storage.storage.clear();
        String expected = VALID_REPORT_EMPTY;
        String actual = reportService.generateReport();
        assertEquals(expected,actual);
    }
}
