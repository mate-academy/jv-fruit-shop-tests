package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String FIRST_ROW = "fruit,quantity\n";
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void validReport_Ok() {
        Storage.STORAGE.put("banana", 20);
        Storage.STORAGE.put("apple", 15);
        String expected = FIRST_ROW
                + "banana,20\r\n"
                + "apple,15\r\n";
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void emptyReport_NotOk() {
        assertThrows(FruitShopException.class, () -> reportService.createReport());
    }
}
