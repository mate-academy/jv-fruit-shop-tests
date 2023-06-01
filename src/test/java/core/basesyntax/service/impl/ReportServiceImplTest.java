package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String HEAD_STRING = "fruit,quantity" + LINE_SEPARATOR;
    private static ReportService reporter;

    @BeforeAll
    public static void beforeAll() {
        reporter = new ReportServiceImpl();
    }

    @AfterEach
    public void tearDown() {
        Storage.FRUITS.clear();
    }

    @Test
    public void report_Empty_Storage_ok() {
        String expected = HEAD_STRING + LINE_SEPARATOR;
        String actual = reporter.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void report_With_One_Item_ok() {
        Storage.FRUITS.put("apple", 10);
        String expected = HEAD_STRING
                + "apple,10"
                + LINE_SEPARATOR;
        String actual = reporter.createReport();
        assertEquals(expected, actual);
    }

    @Test
    public void report_Storage_With_Multiple_Items_ok() {
        Storage.FRUITS.put("apple", 10);
        Storage.FRUITS.put("orange", 20);
        Storage.FRUITS.put("banana", 30);
        String expected = "fruit,quantity"
                + LINE_SEPARATOR
                + "orange,20"
                + LINE_SEPARATOR
                + "banana,30"
                + LINE_SEPARATOR
                + "apple,10"
                + LINE_SEPARATOR;
        String actual = reporter.createReport();
        assertEquals(expected, actual);
    }
}
