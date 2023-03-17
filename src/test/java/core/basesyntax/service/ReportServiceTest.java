package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceTest {
    private static ReportService reportService;
    private static StringBuilder expected;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String TITLE = "fruit,quantity";
    private static final String FIRST_LINE = "banana,20";
    private static final String SECOND_LINE = "apple,90";

    @BeforeClass
    public static void beforeAll() {
        reportService = new ReportServiceImpl();
        expected = new StringBuilder();
        expected.append(TITLE);
        expected.append(System.lineSeparator()).append(FIRST_LINE);
        expected.append(System.lineSeparator()).append(SECOND_LINE);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void reportService_createReport_Ok() {
        Storage.fruits.put(BANANA, 20);
        Storage.fruits.put(APPLE, 90);
        String actual = reportService.createReport();
        assertEquals(expected.toString(), actual);
    }

    @Test
    public void reportService_createWithEmptyStorage_Ok() {
        reportService.createReport();
    }
}
