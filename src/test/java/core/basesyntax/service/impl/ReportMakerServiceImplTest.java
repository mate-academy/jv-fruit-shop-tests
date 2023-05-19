package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportMakerService;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportMakerServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String HEAD_STRING = "fruit,quantity" + LINE_SEPARATOR;
    private static ReportMakerService reportMaker;

    @BeforeClass
    public static void beforeAll() {
        reportMaker = new ReportMakerServiceImpl();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void report_Empty_Storage_Ok() {
        String expected = HEAD_STRING + LINE_SEPARATOR;
        String actual = reportMaker.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    public void report_With_One_Item_ok() {
        Storage.storage.put("apple", 10);
        String expected = HEAD_STRING
                + "apple,10"
                + LINE_SEPARATOR;
        String actual = reportMaker.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    public void report_Storage_With_Multiple_Items_ok() {
        Storage.storage.put("apple", 10);
        Storage.storage.put("orange", 20);
        Storage.storage.put("banana", 30);
        String expected = "fruit,quantity"
                + LINE_SEPARATOR
                + "orange,20"
                + LINE_SEPARATOR
                + "banana,30"
                + LINE_SEPARATOR
                + "apple,10"
                + LINE_SEPARATOR;
        String actual = reportMaker.generateReport();
        assertEquals(expected, actual);
    }
}