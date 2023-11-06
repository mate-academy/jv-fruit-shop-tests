package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportService;
    private static final String SEPARATOR = System.lineSeparator();
    private static final String EXPECTED_REPORT_HEADER = "fruit,quantity";

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReport_validDataInStorage_ok() {
        Storage.fruitTransactions.put("banana", 100);
        Storage.fruitTransactions.put("apple", 300);
        String expected = EXPECTED_REPORT_HEADER + SEPARATOR
                + "banana,100" + SEPARATOR + "apple,300" + SEPARATOR;
        assertEquals(expected, reportService.createReport());
    }

    @AfterEach
    void tearDown() {
        Storage.fruitTransactions.clear();
    }
}
