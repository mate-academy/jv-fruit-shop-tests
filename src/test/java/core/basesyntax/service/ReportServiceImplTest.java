package core.basesyntax.service;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static StringBuilder fullStorageReport;
    private static StringBuilder emptyStorageReport;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
        fullStorageReport = new StringBuilder();
        emptyStorageReport = new StringBuilder();
        fullStorageReport.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana")
                .append(",")
                .append(20)
                .append(System.lineSeparator());
        Storage.storage.put("banana", 20);
        emptyStorageReport.append("fruit,quantity")
                .append(System.lineSeparator());
    }

    @Test
    public void createReport_Ok() {
        String expected = fullStorageReport.toString();
        String actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReportWithEmptyStorage() {
        Storage.storage.clear();
        String expected = emptyStorageReport.toString();
        String actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
