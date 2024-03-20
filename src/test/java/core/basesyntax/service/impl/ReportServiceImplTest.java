package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        Storage.fruitsStorage.clear();
    }

    @Test
    void createReport_ValidData_ok() {
        Storage.fruitsStorage.put("apple", 10);
        StringBuilder reportBuilder =
                new StringBuilder("fruit,quantity" + System.lineSeparator());
        reportBuilder.append("apple,10").append(System.lineSeparator());
        assertEquals(reportBuilder.toString(), reportService.createReport());
    }

    @Test
    void createReport_withEmptyData_ok() {
        String expected = "fruit,quantity" + System.lineSeparator();
        assertEquals(expected, reportService.createReport());
    }
}
