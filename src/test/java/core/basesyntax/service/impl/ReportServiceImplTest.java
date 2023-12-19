package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitDB;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {

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
        String expectedReport = "Fruit,Value" + System.lineSeparator()
                + "banana.152" + System.lineSeparator()
                + "apple.90";
        String actualReport = reportService.makeReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void makeReport_addNewFruits_ok() {
        FruitDB.FRUIT_DATA_BASE.put("grape", 75);
        String expectedReport = "Fruit,Value" + System.lineSeparator()
                + "banana.152" + System.lineSeparator()
                + "apple.90" + System.lineSeparator()
                + "grape.75";
        String actualReport = reportService.makeReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void makeReport_missingFruits_ok() {
        FruitDB.FRUIT_DATA_BASE.remove("banana");
        String actualReport = reportService.makeReport();
        String expectedReport = "Fruit,Value" + System.lineSeparator() + "apple.90";
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
