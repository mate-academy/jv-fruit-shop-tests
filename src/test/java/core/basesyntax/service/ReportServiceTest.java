package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final String HEADER = "fruit,quantity" + System.lineSeparator();
    private static ReportService reportService;
    private static StorageDaoImpl storageDao = new StorageDaoImpl();

    @BeforeAll
    static void setUp() {
        reportService = new ReportService(storageDao);
    }

    @Test
    void createReport_validOutput_Ok() {
        String expected =
                HEADER
                + "banana,107" + System.lineSeparator()
                + "apple,108" + System.lineSeparator();

        Storage.foodStorage.put("banana", 107);
        Storage.foodStorage.put("apple", 108);
        assertEquals(expected, reportService.createReport());
    }
}
