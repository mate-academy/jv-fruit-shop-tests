package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String TITLE = "fruit,quantity";
    private static final String FIRST_PAIR = "banana,152";
    private static final String SECOND_PAIR = "apple,90";
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void createReportService_Ok() {
        assertEquals(TITLE, reportService.createReport());
        Storage.getStorage().put("banana", 152);
        String expected1 = TITLE + System.lineSeparator() + FIRST_PAIR;
        assertEquals(expected1, reportService.createReport());
        Storage.getStorage().put("apple", 90);
        String expected2 = expected1 + System.lineSeparator() + SECOND_PAIR;
        assertEquals(expected2, reportService.createReport());
    }

    @Test
    void createReportQuantityLessThanZero_NotOk() {
        Storage.getStorage().put("banana", -13);
        assertThrows(RuntimeException.class, () -> reportService.createReport());
    }
}
