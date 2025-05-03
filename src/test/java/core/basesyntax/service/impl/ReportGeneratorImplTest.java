package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    @AfterEach
    void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    void generateReport_emptyStorage_ok() {
        ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();
        String expectedReport = "fruit,quantity" + System.lineSeparator();
        String actualReport = reportGenerator.generateReport();
        assertEquals(expectedReport, actualReport, "Report for empty storage is incorrect.");
    }

    @Test
    void generateReport_singleEntry_ok() {
        Storage.fruitStorage.put("apple", 10);
        ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "apple,10";
        String actualReport = reportGenerator.generateReport();
        assertEquals(expectedReport, actualReport, "Report for single entry is incorrect.");
    }

    @Test
    void generateReport_multipleEntries_ok() {
        Storage.fruitStorage.put("apple", 10);
        Storage.fruitStorage.put("banana", 25);
        Storage.fruitStorage.put("cherry", 5);
        ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,25" + System.lineSeparator()
                + "apple,10" + System.lineSeparator()
                + "cherry,5";
        String actualReport = reportGenerator.generateReport();
        assertEquals(expectedReport, actualReport, "Report for multiple entries is incorrect.");
    }

    @Test
    void generateReport_largeQuantities_ok() {
        Storage.fruitStorage.put("mango", Integer.MAX_VALUE);
        ReportGeneratorImpl reportGenerator = new ReportGeneratorImpl();
        String expectedReport = "fruit,quantity" + System.lineSeparator()
                + "mango," + Integer.MAX_VALUE;
        String actualReport = reportGenerator.generateReport();
        assertEquals(expectedReport, actualReport, "Report for large quantities is incorrect.");
    }
}
