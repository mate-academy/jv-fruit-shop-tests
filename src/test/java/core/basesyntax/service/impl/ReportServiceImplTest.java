package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReport_withNullInitializeList_notOk() {
        List<String> report = reportService.createReport();
        assertNotNull(report);
    }

    @Test
    void createReport_validData_ok() {
        Storage.storage.put("apple", 70);
        Storage.storage.put("banana", 80);
        List<String> expected = List.of("fruit,quantity", "banana,80", "apple,70");
        List<String> actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
