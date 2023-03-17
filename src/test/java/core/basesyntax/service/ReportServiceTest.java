package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static StringBuilder expected;

    @BeforeClass
    public static void beforeAll() {
        Storage.fruits.clear();
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("apple", 90);
        expected = new StringBuilder();
        expected.append("fruit,quantity");
        expected.append(System.lineSeparator()).append("banana,20");
        expected.append(System.lineSeparator()).append("apple,90");
    }

    @Test
    public void createReport_Ok() {
        ReportService reportService = new ReportServiceImpl();
        String actual = reportService.createReport();
        assertEquals(expected.toString(), actual);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
