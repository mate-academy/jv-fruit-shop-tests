package core.basesyntax.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {

    private static ReportServiceImpl reportService;

    private Map<String, Integer> mapDB;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    void setUp() {
        mapDB = new HashMap<>();
        mapDB.put("apple", 100);
        mapDB.put("banana", 150);
    }

    @Test
    void prepareReport_returnString_Ok() {
        String report = reportService.prepareReport(mapDB);
        Assertions.assertFalse(report.isEmpty());
    }

    @Test
    void prepareReport_rightFormat_Ok() {
        String report = reportService.prepareReport(mapDB);

        String expect = "fruit,quantity"
                + System.lineSeparator()
                + "banana,150"
                + System.lineSeparator()
                + "apple,100"
                + System.lineSeparator();

        Assertions.assertTrue(expect.equals(report));
    }
}
