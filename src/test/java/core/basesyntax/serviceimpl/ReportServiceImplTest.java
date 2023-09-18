package core.basesyntax.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String HEADERS = "fruit,quantity" + System.lineSeparator();

    @AfterEach
    void clear() {
        Storage.storage.clear();
    }

    @Test
    void when_EmptyMap_Ok() {
        String expected = HEADERS;
        ReportService reportService = new ReportServiceImpl();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void when_ValidData_Ok() {
        String expected = HEADERS + "apple,100" + System.lineSeparator();
        Storage.storage.put("apple", 100);
        ReportService reportService = new ReportServiceImpl();
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
