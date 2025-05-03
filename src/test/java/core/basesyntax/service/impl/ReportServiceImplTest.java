package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @AfterEach
    void afterEach() {
        Storage.fruitStorage.clear();
    }

    @Test
    void createReport_validData_Ok() {
        Storage.fruitStorage.put("banana",25);
        Storage.fruitStorage.put("apple",5);
        String expectedReportString = "fruit,quantity" + System.lineSeparator()
                + "banana,25" + System.lineSeparator()
                + "apple,5";
        assertEquals(expectedReportString,reportService.createReport());
    }

    @Test
    void createReport_emptyData_Ok() {
        assertEquals("fruit,quantity", reportService.createReport());
    }
}
