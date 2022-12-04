package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String HEADER = "fruit,quantity";
    private ReportService report;

    @Before
    public void setUp() {
        report = new ReportServiceImpl();
    }

    @Test
    public void createReport_ok() {
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 3);
        String actual = report.createReport();
        String expected = new StringBuilder().append(HEADER + System.lineSeparator() + "banana,20"
                + System.lineSeparator() + "apple,3").toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createEmptyReport_ok() {
        String actual = report.createReport();
        String expected = HEADER;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
