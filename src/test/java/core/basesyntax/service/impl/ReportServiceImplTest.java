package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private final String firstLinePattern = "fruit,quantity" + System.lineSeparator();
    private ReportService reportService;

    @BeforeEach
     void setUp() {
        reportService = new ReportServiceImpl();
    }

    @AfterEach
    void clearStorage() {
        Storage.storage.clear();
    }

    @Test
    void emptyStorageValidReport_Ok() {
        Assertions.assertEquals(firstLinePattern,reportService.generateReport());
    }

    @Test
    void generateValidReport_ok() {
        Storage.storage.put(new Fruit("banana"), 10);
        Storage.storage.put(new Fruit("apple"), 5);
        String report = reportService.generateReport();
        String expected = new StringBuilder("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,10")
                .append(System.lineSeparator())
                .append("apple,5")
                .append(System.lineSeparator())
                .toString();
        Assertions.assertEquals(expected,report);
    }
}
