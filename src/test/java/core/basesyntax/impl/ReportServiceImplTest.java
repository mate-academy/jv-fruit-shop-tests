package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.services.ReportService;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String FIRST_LINE_REPORT = "fruit,quantity"
            + System.lineSeparator();

    private final ReportService reportService = new ReportServiceImpl();

    @Test
    public void createReport_ok() {
        Storage.fruits.put("banana",20);
        Storage.fruits.put("apple",100);
        String expectedReport = FIRST_LINE_REPORT
                + "banana,20" + System.lineSeparator() + "apple,100" + System.lineSeparator();
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void createReportWithEmptyStorage_ok() {
        Storage.fruits.clear();
        String actualReport = reportService.createReport();
        assertEquals(FIRST_LINE_REPORT, actualReport);
    }
}
