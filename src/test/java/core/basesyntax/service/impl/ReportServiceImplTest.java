package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReportServiceImplTest {
    private static final String REPORT_LEGEND = "fruit,quantity";
    private static final String SEPARATOR = ",";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private StringBuilder sb;
    private final String key = "testItem";
    private final int value = 1;
    private final ReportServiceImpl reportService = new ReportServiceImpl();

    @Before
    public void setUp() {
        sb = new StringBuilder(REPORT_LEGEND).append(System.lineSeparator());
        Storage.storeItems.put(key, value);
    }

    @After
    public void tearDown() {
        Storage.storeItems.clear();
    }

    @Test
    public void getReport() {
        String expected = sb.append(key).append(SEPARATOR).append(value).toString();
        String actual = reportService.getReport(reportService.getDataForReport());
        assertEquals(expected, actual);
    }

    @Test
    public void getDataForReportEmptyStorage_NotOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(("Storage is empty"));
        Storage.storeItems.clear();
        reportService.getDataForReport();
    }
}
