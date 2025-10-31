package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void initializeService() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void cleanStorage() {
        Storage.cleanStorage();
    }

    @Test
    void getReport_emptyStorage_ok() {
        String expected = "fruit,quantity";
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual, "can't generate report for empty Storage");
    }

    @Test
    void getReport_StorageWithProducts_ok() {
        String expectedHeader = "fruit,quantity";
        String expectedApple = System.lineSeparator() + "apple,1000";
        String expectedBanana = System.lineSeparator() + "potato,500";

        Storage.setFruitQuantity("potato", 500);
        Storage.setFruitQuantity("apple", 1000);
        String actual = reportGenerator.getReport();
        assertTrue(actual.contains(expectedHeader)
                        && actual.contains(expectedApple)
                        && actual.contains(expectedBanana),
                "can't generate report for not empty Storage");
    }
}
