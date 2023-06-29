package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void getDataFromReportReportGeneratorFromEmptyStorage_Ok() {
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportGenerator.getDataForReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void getDataForReportReportGenerator_Ok() {
        Storage.storage.put("apple", 50);
        Storage.storage.put("banana", 100);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,50" + System.lineSeparator();
        String actualReport = reportGenerator.getDataForReport();
        assertEquals(expectedReport, actualReport);
    }

    @AfterAll
    static void afterAll() {
        Storage.storage.clear();
    }
}
