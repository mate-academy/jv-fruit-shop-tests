package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReportServiceImplTest {
    public static final String HEAD = "fruit,quantity";
    public static final String SEPARATOR = System.lineSeparator();

    @Test
    public void reportServiceHead_OK() {
        ReportService reportService = new ReportServiceImpl();
        String actualResult = reportService.generateReport();
        String headString = HEAD + SEPARATOR;
        Assert.assertEquals(actualResult,headString);
    }

    @Test
    public void reportService_OK() {
        StringBuilder stringBuilder = new StringBuilder(HEAD).append(SEPARATOR);
        String correctString = stringBuilder.append("banana,152").append(SEPARATOR)
                                            .append("apple,90").append(SEPARATOR)
                                            .toString();
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        String actualReport = new ReportServiceImpl().generateReport();
        Assert.assertEquals(correctString, actualReport);
    }

    @Test
    public void reportService_NotOK() {
        StringBuilder stringBuilder = new StringBuilder(HEAD).append(SEPARATOR);
        String correctString = stringBuilder.append("banana,152")
                                            .append(SEPARATOR)
                                            .append("apple,90").append(SEPARATOR)
                                            .toString();
        Storage.fruits.put("banana", 15);
        Storage.fruits.put("apple", 9);
        String actualReport = new ReportServiceImpl().generateReport();
        Assert.assertNotEquals(correctString, actualReport);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
