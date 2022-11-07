package core.basesyntax;

import core.basesyntax.database.Storage;
import core.basesyntax.service.ReportService;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static StringBuilder validReport;
    private static StringBuilder validReportEmptyFile;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
        validReport = new StringBuilder();
        validReport.append("fruit,quantity");
        validReport.append(System.lineSeparator());
        validReport.append("banana,25");
        validReport.append(System.lineSeparator());
        validReport.append("apple,25");
        validReport.append(System.lineSeparator());
        validReportEmptyFile = new StringBuilder();
        validReportEmptyFile.append("fruit,quantity");
        validReportEmptyFile.append(System.lineSeparator());
    }

    @Test
    public void generateReport_EmptyStorage_ok() {
        String expected = validReportEmptyFile.toString();
        String actual = reportService.generateReport();
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void generateReport_filledStorage_ok() {
        Storage.storage.put("banana", 25);
        Storage.storage.put("apple", 25);
        String expected = validReport.toString();
        String actual = reportService.generateReport();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
