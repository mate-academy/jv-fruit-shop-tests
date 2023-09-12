package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static String reportHeader;
    private static ReportService reportService;
    private static String lineSeparator;

    @BeforeAll
    static void beforeAll() {
        reportHeader = "fruit,quantity";
        lineSeparator = System.lineSeparator();
        reportService = new ReportServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void reportService_validData_ok() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
        String expected = reportHeader + lineSeparator + "banana,152" + lineSeparator + "apple,90";
        assertEquals(expected, reportService.createReport());
    }

    @Test
    void reportService_wrongData_notOk() {
        Storage.fruits.put("banana", 150);
        Storage.fruits.put("apple", 90);
        String expected = reportHeader + lineSeparator + "banana,152" + lineSeparator + "apple,90";
        assertNotEquals(expected, reportService.createReport());
    }

    @Test
    void reportService_emptyStorage_notOk() {
        String expected = reportHeader + lineSeparator + "banana,152" + lineSeparator + "apple,90";
        assertNotEquals(expected, reportService.createReport());
    }

    @Test
    void reportService_emptyStorage_Ok() {
        assertEquals(reportHeader, reportService.createReport());
    }
}
