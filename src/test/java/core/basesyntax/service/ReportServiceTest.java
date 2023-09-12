package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static ReportService reportService;
    private static final String DEFAULT_TITTLE = "fruit,quantity" + System.lineSeparator();

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void createReportText_ValidTextInput_IsOk() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        String expected = DEFAULT_TITTLE
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        String actual = reportService.createReportText();
        assertEquals(expected, actual);
    }

    @Test
    void createReportText_FromEmptyStorage_IsOk() {
        String expected = DEFAULT_TITTLE;
        String actual = reportService.createReportText();
        assertEquals(expected, actual);
    }
}
