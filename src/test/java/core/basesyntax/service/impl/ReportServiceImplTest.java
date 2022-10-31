package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportServiceImplTest {
    private static final String END_LINE = System.lineSeparator();
    private static final String COLUMNS = "fruit,quantity" + END_LINE;
    private static ReportService reportService;

    @BeforeClass
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Before
    public void init() {
        Storage.fruits.put("kiwi", 12);
        Storage.fruits.put("banana", 23);
        Storage.fruits.put("apple", 90);
        Storage.fruits.put("orange", 45);
    }

    @Test
    public void getReport_Ok() {
        String expected = COLUMNS
                + "apple,90" + END_LINE
                + "banana,23" + END_LINE
                + "kiwi,12" + END_LINE
                + "orange,45";
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    public void getReport_NullStorage_NotOk() {
        Storage.fruits.clear();
        assertThrows(RuntimeException.class, () -> reportService.getReport());
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}
