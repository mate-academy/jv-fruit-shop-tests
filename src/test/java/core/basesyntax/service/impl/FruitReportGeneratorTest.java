package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitReportGeneratorTest {
    private static ReportGenerator reportGenerator;

    @BeforeAll
    public static void setUpAll() {
        reportGenerator = new FruitReportGenerator();
    }

    @BeforeEach
    public void setUp() {
        Storage.fruits.put("banana", 152);
        Storage.fruits.put("apple", 90);
    }

    @AfterEach
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void generateReport_noItems_ok() {
        Storage.fruits.clear();
        String expected = "fruit,quantity" + System.lineSeparator();
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    public void generateReport_oneItem_ok() {
        Storage.fruits.remove("banana");
        String expected = "fruit,quantity" + System.lineSeparator()
                          + "apple,90" + System.lineSeparator();
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }

    @Test
    public void generateReport_severalItems_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                          + "banana,152" + System.lineSeparator()
                          + "apple,90" + System.lineSeparator();
        String actual = reportGenerator.generateReport();
        assertEquals(expected, actual);
    }
}
