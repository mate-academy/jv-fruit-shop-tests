package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorServiceImplTest {
    private static int COMMA_SEPARATOR_INDEX = 5;
    private static int LINE_SEPARATOR_INDEX = 14;
    private static ReportGeneratorServiceImpl reportGeneratorService;

    @BeforeAll
    static void beforeAll() {
        reportGeneratorService = new ReportGeneratorServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void generateReport_generateNotNullReport_Ok() {
        Storage.STORAGE.put("banana", 40);
        Storage.STORAGE.put("apple", 30);
        String actual = reportGeneratorService.generateReport();
        assertNotNull(actual);
    }

    @Test
    void generateReport_generateReportWithNullKey_NotOk() {
        Storage.STORAGE.put(null, 10);
        assertThrows(IllegalArgumentException.class, () -> reportGeneratorService.generateReport());
    }

    @Test
    void generateReport_firstExplanatoryWord_Ok() {
        String report = reportGeneratorService.generateReport();
        String actual = report.substring(0, COMMA_SEPARATOR_INDEX);
        String expected = "fruit";
        assertEquals(expected,actual);
    }

    @Test
    void generateReport_secondExplanatoryWord_Ok() {
        String report = reportGeneratorService.generateReport();
        String actual = report.substring(COMMA_SEPARATOR_INDEX + 1, LINE_SEPARATOR_INDEX);
        String expected = "quantity";
        assertEquals(expected,actual);
    }

    @Test
    void generateReport_generateCorrectReport_Ok() {
        Storage.STORAGE.put("oranges", 10);
        Storage.STORAGE.put("banana", 20);
        Storage.STORAGE.put("apple", 100);
        String actual = reportGeneratorService.generateReport();
        String expected = getCorrectReport();
        assertEquals(expected, actual);
    }

    private String getCorrectReport() {
        String report = "fruit,quantity"
                + System.lineSeparator() + "banana,20"
                + System.lineSeparator() + "apple,100"
                + System.lineSeparator() + "oranges,10";
        return report;
    }
}
