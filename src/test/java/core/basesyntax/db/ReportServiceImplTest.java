package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void returnsCorrectData_Ok() {
        Storage.storage.put("banana", 20);
        Storage.storage.put("apple", 100);
        StringBuilder builder = new StringBuilder();
        builder.append("fruit, quantity")
                .append(System.lineSeparator())
                .append("banana,20")
                .append(System.lineSeparator())
                .append("apple,100")
                .append(System.lineSeparator());
        String expectedData = builder.toString();
        String actualData = reportService.generate();
        assertEquals(expectedData, actualData);
    }

    @Test
    void returnsCorrectData_emptyStorage_Ok() {
        String expectedData = "fruit, quantity" + System.lineSeparator();
        String actualData = reportService.generate();
        assertEquals(expectedData, actualData);
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }
}
