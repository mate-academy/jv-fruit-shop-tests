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
    private static StringBuilder validReport;
    private static final String VALID_REPORT_EMPTY = "fruit,quantity" + System.lineSeparator();

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
        storage = new Storage();
        storage.storage.put("banana", 25);
        storage.storage.put("apple", 25);
        validReport = new StringBuilder();
        validReport.append("fruit,quantity");
        validReport.append(System.lineSeparator());
        validReport.append("banana,25");
        validReport.append(System.lineSeparator());
        validReport.append("apple,25");
        validReport.append(System.lineSeparator());
    }

    @Test
    public void generateReportEmptyStorage_Ok() {
        storage.storage.clear();
        String expected = VALID_REPORT_EMPTY;
        String actual = reportService.generateReport();
        assertEquals(expected,actual);
    }

    @Test
    public void generateReport_Ok() {
        String expected = validReport.toString();
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
    }
}
