package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportServiceImplTest {

    private static final String HEAD_STRING = "fruit,quantity" + System.lineSeparator();
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
    public void report_WithOneItem_Ok() {
        Storage.storage.put("apple", 10);
        String expected = HEAD_STRING + "apple,10"
                + System.lineSeparator();
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    public void report_StorageWithMultipleItem_Ok() {
        Storage.storage.put("apple", 10);
        Storage.storage.put("orange", 20);
        Storage.storage.put("banana", 30);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "orange,20"
                + System.lineSeparator()
                + "banana,30"
                + System.lineSeparator()
                + "apple,10"
                + System.lineSeparator();
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
    }
}
