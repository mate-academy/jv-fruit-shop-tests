package core.basesyntax.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.Storage;
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
    void create_ReportService_Ok() {
        assertEquals(TITLE + System.lineSeparator(), reportService.createReport());
        Storage.getStorage().put("banana", 152);
        String expected1 = TITLE + System.lineSeparator() + FIRST_PAIR;
        assertEquals(expected1 + System.lineSeparator(), reportService.createReport());
        Storage.getStorage().put("apple", 90);
        String expected2 = expected1 + System.lineSeparator() + SECOND_PAIR;
        assertEquals(expected2 + System.lineSeparator(), reportService.createReport());
    }

    @Test
    void create_ReportQuantityLessThanZero_NotOk() {
        Storage.getStorage().put("banana", -13);
        assertThrows(RuntimeException.class, () -> reportService.createReport());
    }
}
