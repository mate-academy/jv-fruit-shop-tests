package core.basesyntax.shop.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.shop.db.Storage;
import core.basesyntax.shop.service.ReportService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReportServiceImplTest {
    private static ReportService reportGenerator;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportServiceImpl();
    }

    @Test
    void getDataFromEmptyReport_Ok() {
        String expectedReport = "fruit,quantity";
        String actualReport = reportGenerator.generateReport();
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void getDataNonEmptyReport_Ok() {
        Storage.fruits.put("apple", 50);
        Storage.fruits.put("banana", 100);
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,50";
        String actualReport = reportGenerator.generateReport();
        assertEquals(expectedReport, actualReport);
    }

    @AfterEach
    public void afterEach() {
        Storage.fruits.clear();
    }

}
