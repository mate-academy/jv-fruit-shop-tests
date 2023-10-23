package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportGenerator;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        Storage.storage.clear();
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void getCorrectReport_Ok() {
        Storage.storage.put("banana", 20);
        Storage.storage.put("apple", 100);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,20" + System.lineSeparator()
                + "apple,100" + System.lineSeparator();

        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void getOnlyHeaderOfReport_Ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void zeroQuantityOfFruit_Ok() {
        Storage.storage.put("banana", 0);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,0" + System.lineSeparator();
        String actualReport = reportGenerator.getReport();
        assertEquals(expectedReport, actualReport);
    }
}
