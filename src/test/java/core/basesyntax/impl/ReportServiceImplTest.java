package core.basesyntax.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static ReportService reportService;
    private static final String ENTER = System.lineSeparator();

    @BeforeClass
    public static void beforeClass() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void setUp() {
        Storage.fruits.put("grape", 46);
        Storage.fruits.put("banana", 24);
        Storage.fruits.put("mango", 30);
        Storage.fruits.put("apricot", 80);
        Storage.fruits.put("apple", 120);
    }

    @Test
    public void createReport_Ok() {
        String actual = reportService.writeReport(Storage.fruits);
        String expected = "fruit,quantity" + ENTER + "banana,24" + ENTER
                + "apricot,80" + ENTER + "apple,120" + ENTER + "grape,46" + ENTER
                + "mango,30" + ENTER;
        assertEquals(expected, actual);
    }

    @Test
    public void createReport_emptyStorage_Ok() {
        Storage.fruits.clear();
        String actual = reportService.writeReport(Storage.fruits);
        String expected = "fruit,quantity" + ENTER;
        assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
