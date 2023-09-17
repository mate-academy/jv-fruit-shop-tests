package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import db.Warehouse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.ReportService;

class ReportServiceImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Warehouse.STORAGE.clear();
    }

    @Test
    void createReport_validData_ok() {
        Warehouse.STORAGE.put("banana", 152);
        Warehouse.STORAGE.put("apple", 90);
        final String expected = "fruit,quantity" + System.lineSeparator() + "banana,152"
                + System.lineSeparator() + "apple,90";
        assertEquals(expected, reportService.createReport());
    }

    @Test
    void createReport_emptyReport_ok() {
        assertEquals("fruit,quantity", reportService.createReport());
    }
}
