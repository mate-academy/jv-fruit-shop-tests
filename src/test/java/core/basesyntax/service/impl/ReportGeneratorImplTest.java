package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
        Storage.fruits.clear();
    }

    @Test
    void getReport_EmptyStorage_ShouldReturnOnlyHeader() {
        String actualReport = reportGenerator.getReport();
        assertEquals("fruit,quantity\n", actualReport);
    }

    @Test
    void getReport_OneFruitInStorage_ShouldReturnCorrectReport() {
        Storage.fruits.put("apple", 12);
        String actualReport = reportGenerator.getReport();
        String expectedReport = "fruit,quantity\napple,12\n";
        assertEquals(expectedReport, actualReport);
    }

    @Test
    void getReport_MultipleFruitsInStorage_ShouldReturnCorrectReport() {
        Storage.fruits.put("banana", 4);
        Storage.fruits.put("apple", 12);
        String actualReport = reportGenerator.getReport();
        String expectedReport = "fruit,quantity\nbanana,4\napple,12\n";
        assertEquals(expectedReport, actualReport);
    }
}
