package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String HEADERS = "fruit,quantity" + System.lineSeparator();
    private static ReportService reportService;

    @BeforeAll
    static void init() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    void clearAfter() {
        Storage.storage.clear();
    }

    @Test
    void when_EmptyMap_Ok() {
        String expected = HEADERS;
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void when_ValidData_Ok() {
        String expected = HEADERS + "apple,100" + System.lineSeparator();
        Storage.storage.put("apple", 100);
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
