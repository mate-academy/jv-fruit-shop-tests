package core.basesyntax.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static final String HEADER = "fruit, quantity";
    private static final String FIRST_FRUIT = "banana,10";
    private static final String SECOND_FRUIT = "apple,5";
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
    }

    @Test
    void createReportReturnOk() {
        Storage.dataBase.put("banana", 10);
        Storage.dataBase.put("apple", 5);
        String expectedReport = HEADER
                + System.lineSeparator()
                + FIRST_FRUIT
                + System.lineSeparator()
                + SECOND_FRUIT;
        String actualReport = reportService.createReport();
        assertEquals(expectedReport, actualReport);
    }

    @AfterEach
    public void afterEachTest() {
        Storage.dataBase.clear();
    }
}
