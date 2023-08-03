package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {

    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String HEAD_STRING = "fruit,quantity" + LINE_SEPARATOR;
    private static ReportServiceImpl reportService;

    @BeforeAll
    public static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        Storage.storage.clear();
    }

    @Test
    public void report_EmptyStorage_Ok() {
        String expected = HEAD_STRING + LINE_SEPARATOR;
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    public void report_WithOneItem_Ok() {
        Storage.storage.put("apple", 10);
        String expected = HEAD_STRING + "apple,10"
                + LINE_SEPARATOR;
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    public void report_StorageWithMultipleItem_Ok() {
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
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
    }
}
