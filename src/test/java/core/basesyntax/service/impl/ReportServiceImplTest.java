package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    public static final String DEFAULT_FRUIT = "banana";
    public static final String SECOND_DEFAULT_FRUIT = "apple";
    public static final int DEFAULT_QUANTITY = 100;
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void generateReportNullValue_NotOk() {
        assertThrows(NullPointerException.class,
                () -> reportService.generateReport(null));
    }

    @Test
    void generateReportValidData_Ok() {
        Storage.storage.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        Storage.storage.put(SECOND_DEFAULT_FRUIT, DEFAULT_QUANTITY);
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();
        assertEquals(expected, reportService.generateReport(Storage.storage));
    }
}
