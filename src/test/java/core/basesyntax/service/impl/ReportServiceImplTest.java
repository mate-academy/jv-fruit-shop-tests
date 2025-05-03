package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String REPORT_LEGEND = "fruit,quantity\n";
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void validReport_Ok() {
        Storage.FRUITS.put("banana", 20);
        Storage.FRUITS.put("apple", 15);
        String expected = REPORT_LEGEND + "banana,20\n" + "apple,15\n";
        String actual = reportService.createReport();
        assertEquals(expected, actual);
    }

    @Test
    void emptyReport_notOk() {
        assertThrows(FruitShopException.class, () -> reportService.createReport());
    }
}
