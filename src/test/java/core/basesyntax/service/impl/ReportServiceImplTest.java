package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String REPORT = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private static final String EMPTY_REPORT = "fruit,quantity" + System.lineSeparator();
    private ReportService reportService;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    public void createReport_Ok() {
        Storage.storage.put("banana", 152);
        Storage.storage.put("apple", 90);
        String actual = reportService.generateReport();
        Assert.assertEquals(REPORT, actual);
    }

    @Test
    public void createReport_EmptyData_Ok() {
        String expectedOutput = EMPTY_REPORT;
        String actualOutput = reportService.generateReport();
        Assert.assertEquals(expectedOutput, actualOutput);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
