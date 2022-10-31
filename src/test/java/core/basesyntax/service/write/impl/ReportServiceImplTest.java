package core.basesyntax.service.write.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.write.ReportService;
import org.junit.After;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final ReportService reportService = new ReportServiceImpl();

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void createReport_ok() {
        Storage.storage.put("banana", 200);
        Storage.storage.put("apple", 999);
        String expectedReport = "fruit,quantity" + LINE_SEPARATOR
                + "apple,999" + LINE_SEPARATOR
                + "banana,200";
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    public void createReport_emptyStorage_ok() {
        String expectedReport = "fruit,quantity";
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport);
    }
}
