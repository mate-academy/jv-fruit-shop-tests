package core.basesyntax.service;

import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static StringBuilder stringBuilder;

    @Before
    public void setUp() {
        reportService = new ReportServiceImpl();
        stringBuilder = new StringBuilder();
        stringBuilder.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana")
                .append(",")
                .append(20)
                .append(System.lineSeparator());
        Storage.storage.put("banana", 20);
    }

    @Test
    public void createReport_Ok() {
        String expected = stringBuilder.toString();
        String actual = reportService.createReport();
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
