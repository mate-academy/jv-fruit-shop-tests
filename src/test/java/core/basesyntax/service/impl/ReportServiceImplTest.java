package core.basesyntax.service.impl;

import static core.basesyntax.model.Product.APPLE;
import static core.basesyntax.model.Product.BANANA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE_MAP.clear();
    }

    @DisplayName("Check report with banana and apple")
    @Test
    void createReport_bananaAndApple_ok() {
        Storage.STORAGE_MAP.put(BANANA.getName(), 152);
        Storage.STORAGE_MAP.put(APPLE.getName(), 90);
        List<String> expected = List.of("banana,152", "apple,90");
        List<String> actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @DisplayName("Check report with banana")
    @Test
    void createReport_banana_ok() {
        Storage.STORAGE_MAP.put(BANANA.getName(), 99);
        List<String> expected = List.of("banana,99");
        List<String> actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @DisplayName("Check report without any fruit")
    @Test
    void createReport_emptyList_ok() {
        List<String> expected = List.of();
        List<String> actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
