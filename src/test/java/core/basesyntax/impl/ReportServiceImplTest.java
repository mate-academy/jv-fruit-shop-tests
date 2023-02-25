package core.basesyntax.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService service;
    private static final String TITLE = "fruit,quantity";
    private static final String SEPARATOR = System.lineSeparator();

    @Before
    public void setUp() {
        service = new ReportServiceImpl();
    }

    @Test
    public void createCorrectReport_Ok() {
        StringBuilder builder = new StringBuilder();
        String expected = builder.append(TITLE).append(SEPARATOR)
                .append("apple,200").append(SEPARATOR)
                .append("pear,100").append(SEPARATOR).toString();
        Storage.fruits.put("apple", 200);
        Storage.fruits.put("pear", 100);
        String actual = service.createReport();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createReportWithNotCorrectData_NotOk() {
        StringBuilder builder = new StringBuilder();
        String expected = builder.append(TITLE).append(SEPARATOR)
                .append("apple,200").append(SEPARATOR)
                .append("pear,100").append(SEPARATOR).toString();
        Storage.fruits.put("apple", 150);
        Storage.fruits.put("pear", 50);
        String actual = service.createReport();
        Assert.assertNotEquals(expected, actual);
    }

    @After
    public void cleanAfterEach() {
        Storage.fruits.clear();
    }
}
