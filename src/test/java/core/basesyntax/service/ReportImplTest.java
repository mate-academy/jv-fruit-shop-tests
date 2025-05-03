package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.impl.ReportServiceImpl;
import java.util.HashMap;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportImplTest {
    private static ReportService reportService;

    @BeforeAll
    static void beforeAll() {
        reportService = new ReportServiceImpl();
    }

    @AfterAll
    static void afterAll() {
        Storage.fruitData.clear();
    }

    @Test
    void generateReport_emptyMap_NotOk() {
        String actual = reportService.generateReport(new HashMap<>());
        assertEquals("fruit,quantity",actual);
    }

    @Test
    void generateReport_ValidData_Ok() {
        String expect = "fruit,quantity"
                + System.lineSeparator()
                + "banana,90";

        Storage.fruitData.put("banana",90);

        String actual = reportService.generateReport(Storage.fruitData);
        assertEquals(expect,actual);
    }
}
