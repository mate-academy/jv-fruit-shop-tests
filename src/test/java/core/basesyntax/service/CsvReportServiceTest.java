package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.impl.CsvReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CsvReportServiceTest {
    private static final String FIRST_ROW = "fruit,quantity" + System.lineSeparator();
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new CsvReportService();
    }

    @Test
    void generateReport_validData_ok() {
        Storage.FRUITS.put("banana", 152);
        Storage.FRUITS.put("apple", 90);
        String expected = FIRST_ROW
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
        String actual = reportService.generateReport();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void generateReport_noData_ok() {
        String actual = reportService.generateReport();
        Assertions.assertEquals(FIRST_ROW, actual);
    }

    @Test
    void generateReport_nullData_ok() {
        Storage.FRUITS.put(null, null);
        String expected = FIRST_ROW + "null,null" + System.lineSeparator();
        String actual = reportService.generateReport();
        Assertions.assertEquals(expected, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}
