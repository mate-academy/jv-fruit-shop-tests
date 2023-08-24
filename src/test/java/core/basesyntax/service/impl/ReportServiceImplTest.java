package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitDb;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    public static final String FIRST_FRUIT = "firstFruit";
    public static final int TEST_QUANTITY = 50;
    private static ReportService reportService;
    private static final String COMA_SEPARATOR = ",";
    private static final String REPORT_TITLE = "fruit,quantity";

    @BeforeAll
    static void initVariables() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    void setLists() {
        FruitDb.getBalanceMap().clear();
    }

    @Test
    void getReport_emptyDayReportMap_notOk() {
        reportService.getReport();
    }

    @Test
    void getReport_correctDayReportMap_ok() {
        FruitDb.getBalanceMap().put(FIRST_FRUIT, TEST_QUANTITY);

        String expectedReport = REPORT_TITLE
                + System.lineSeparator()
                + FIRST_FRUIT
                + COMA_SEPARATOR
                + TEST_QUANTITY;
        String actualReport = reportService.getReport();

        assertEquals(expectedReport, actualReport);
    }
}
