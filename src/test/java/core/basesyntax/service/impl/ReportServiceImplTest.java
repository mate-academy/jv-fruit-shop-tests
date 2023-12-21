package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitDB;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {

    private static final String TITLE = "Fruit,Value";
    private static final String REPORT_BANANA = "banana.152";
    private static final String REPORT_APPLE = "apple.90";
    private static final String REPORT_GRAPE = "grape.75";
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl();
        FruitDB.FRUIT_DATA_BASE.put("banana", 152);
        FruitDB.FRUIT_DATA_BASE.put("apple", 90);
    }

    @AfterEach
    void tearDown() {
        FruitDB.FRUIT_DATA_BASE.clear();
    }

    @Test
    void makeReport_validData_ok() {
        String expectedReport = TITLE + System.lineSeparator()
                + REPORT_BANANA + System.lineSeparator()
                + REPORT_APPLE;
        String actualReport = reportService.makeReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void makeReport_addNewFruits_ok() {
        String fruit = "grape";
        int quantity = 75;
        FruitDB.FRUIT_DATA_BASE.put(fruit, quantity);
        String expectedReport = TITLE + System.lineSeparator()
                + REPORT_BANANA + System.lineSeparator()
                + REPORT_APPLE + System.lineSeparator()
                + REPORT_GRAPE;
        String actualReport = reportService.makeReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void makeReport_missingFruits_ok() {
        String fruit = "banana";
        FruitDB.FRUIT_DATA_BASE.remove(fruit);
        String actualReport = reportService.makeReport();
        String expectedReport = TITLE + System.lineSeparator() + REPORT_APPLE;
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void makeReport_emptyData_ok() {
        FruitDB.FRUIT_DATA_BASE.clear();
        String expectedReport = "";
        String actualReport = reportService.makeReport();
        assertEquals(expectedReport, actualReport);
    }
}
