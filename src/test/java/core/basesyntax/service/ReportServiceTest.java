package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.database.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceTest {
    private static ReportService report;

    @BeforeAll
    static void beforeAll() {
        report = new ReportServiceImpl();
    }

    @AfterEach
    void afterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    void getReport_withValidData_ok() {
        Storage.STORAGE.put("banana", 50);
        Storage.STORAGE.put("orange", 10);
        Storage.STORAGE.put("apple", 20);
        String expected = "fruit,quantity\nbanana,50\norange,10\napple,20";
        assertEquals(expected, report.getReport());
    }

    @Test
    void getReport_withWithoutData_ok() {
        String expected = "fruit,quantity";
        assertEquals(expected, report.getReport());
    }
}
