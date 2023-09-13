package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CsvReportServiceImplTest {
    private static final int FRUIT_ONE_QUANTITY = 152;
    private static final int FRUIT_TWO_QUANTITY = 90;
    private static final String HEADER = "fruit,quantity";
    private static final String FRUIT_ONE = "banana";
    private static final String FRUIT_TWO = "apple";
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new CsvReportServiceImpl();
    }

    @Test
    void createReport_validData_ok() {
        Storage.fruitMap.put(FRUIT_ONE, FRUIT_ONE_QUANTITY);
        Storage.fruitMap.put(FRUIT_TWO, FRUIT_TWO_QUANTITY);
        String expected = HEADER + System.lineSeparator()
                + FRUIT_ONE + "," + FRUIT_ONE_QUANTITY + System.lineSeparator()
                + FRUIT_TWO + "," + FRUIT_TWO_QUANTITY + System.lineSeparator();
        assertEquals(expected, reportService.createReport(Storage.fruitMap));
    }

    @Test
    void createReport_nullData_notOk() {
        assertThrows(RuntimeException.class, () -> reportService.createReport(null));
    }

    @Test
    void createReport_emptyData_ok() {
        String expected = HEADER + System.lineSeparator();
        assertEquals(expected, reportService.createReport(Storage.fruitMap));
    }

    @AfterEach
    void tearDown() {
        Storage.fruitMap.clear();
    }
}
