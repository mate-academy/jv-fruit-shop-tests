package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.StorageDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportServiceImpl(new StorageDaoImpl());
    }

    @Test
    void generateReport_emptyStorage_Ok() {
        String expected = "fruit,quantity";
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    void generateReport_correctStorage_Ok() {
        Storage.fruits.put("banana", 100);
        Storage.fruits.put("apple", 200);
        String expected = "fruit,quantity" + System.lineSeparator() + "banana,100"
                + System.lineSeparator() + "apple,200";
        String actual = reportService.generateReport();
        assertEquals(expected, actual);
        Storage.fruits.clear();
    }
}
