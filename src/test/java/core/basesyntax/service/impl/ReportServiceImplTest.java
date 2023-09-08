package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitTransactionsMap.clear();
    }

    @Test
    void getReport_emptyList_Ok() {
        String expected = "fruit,quantity";
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_validData_Ok() {
        Storage.fruitTransactionsMap.put("apple", 1);
        Storage.fruitTransactionsMap.put("banana", 2);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,2" + System.lineSeparator()
                + "apple,1";
        String actual = reportService.getReport();
        assertEquals(expected, actual);
    }
}
