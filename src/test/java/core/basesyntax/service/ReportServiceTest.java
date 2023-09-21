package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final String EXPECTED_RESULT = "fruit,quantity" + System.lineSeparator()
            + "banana,30" + System.lineSeparator()
            + "apple,50";
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.STORAGE.clear();
    }

    @Test
    void createReport_correctReport_Ok() {
        Storage.STORAGE.put("banana", 30);
        Storage.STORAGE.put("apple", 50);
        assertEquals(EXPECTED_RESULT, reportService.createReport());
    }
}
