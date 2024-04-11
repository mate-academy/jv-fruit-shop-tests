package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportCreatorServiceImplTest {
    private static ReportCreatorServiceImpl reportCreatorService;

    @BeforeAll
    static void beforeAll() {
        reportCreatorService = new ReportCreatorServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void createReport_validData_ok() {
        Storage.fruitStorage.put("apple", 10);
        Storage.fruitStorage.put("banana", 20);
        List<String> expected = List.of("fruit,quantity", "\r\nbanana,20", "\r\napple,10");
        List<String> actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void createReport_emptyStorage_ok() {
        List<String> expected = List.of("fruit,quantity");
        List<String> actual = reportCreatorService.createReport();
        assertEquals(expected, actual);
    }
}
