package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceTest {

    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportService();
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void createReport_Ok() {
        Storage.fruits.put("test1", 12);
        List<String[]> result = reportService.createReport();
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"fruit", "quantity"});
        expected.add(new String[]{"test1", "12"});
        assertEquals(expected.size(), result.size());
        for (int i = 0; i < expected.size(); i++) {
            assertArrayEquals(expected.get(i), result.get(i));
        }
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
