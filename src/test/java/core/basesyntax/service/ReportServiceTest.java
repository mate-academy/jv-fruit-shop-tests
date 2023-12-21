package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static ReportService reportService;

    @BeforeAll
    public static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    public void initEach() {
        Storage.getFruits().clear();
    }

    @Test
    void createReport_emptyStorage_ok() {
        assertDoesNotThrow(() -> reportService.createReport());
    }

    @Test
    void createReport_allOk() {
        Storage.getFruits().put("banana", 10);
        assertDoesNotThrow(() -> reportService.createReport());
    }
}
