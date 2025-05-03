package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static final String BREAK = System.lineSeparator();
    private static final String HEADER = "fruit,quantity" + BREAK;
    private static ReportService reportService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void setUp() {
        storageDao = new StorageDaoImpl();
        reportService = new ReportService(storageDao);
    }

    @Test
    void createReport_validOutput_ok() {
        String expected =
                HEADER
                + "banana,107" + BREAK
                + "apple,108" + BREAK;

        Storage.foodStorage.put("banana", 107);
        Storage.foodStorage.put("apple", 108);
        var actual = reportService.createReport();
        assertEquals(expected, actual);
    }
}
