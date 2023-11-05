package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

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
    void tearDown() {
        Storage.fruitTransactions.clear();
    }

    @Test
    void createReport_validDataInStorage_ok() {
        String separator = System.lineSeparator();
        Storage.fruitTransactions.put("banana",100);
        Storage.fruitTransactions.put("apple",300);
        String expected = "fruit,quantity" + separator
                + "banana,100" + separator + "apple,300" + separator;
        assertEquals(expected,
                reportService.createReport());
    }
}
