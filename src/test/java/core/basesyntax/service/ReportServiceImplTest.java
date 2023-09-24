package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    //private static final String FIRST_ROW = "fruit,quantity" + System.lineSeparator();
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void create_validReport_Ok() {
        Storage.STORAGE.put("banana", 20);
        Storage.STORAGE.put("apple", 15);
        StringBuilder expected = new StringBuilder();
        expected.append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,20")
                .append(System.lineSeparator())
                .append("apple,15")
                .append(System.lineSeparator());
        String actual = reportService.createReport();
        assertEquals(expected.toString(), actual);
    }

    @Test
    void create_emptyReport_NotOk() {
        assertThrows(FruitShopException.class, () -> reportService.createReport());
    }
}
