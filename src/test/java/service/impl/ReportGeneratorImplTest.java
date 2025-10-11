package service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import db.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ReportGenerator;

public class ReportGeneratorImplTest {

    private final ReportGenerator reportGenerator = new ReportGeneratorImpl();

    @BeforeEach
    void setUp() throws Exception {
        Map<String, Integer> map = getFruitStorage();
        map.clear();
    }

    private Map<String, Integer> getFruitStorage() throws Exception {
        var field = Storage.class.getDeclaredField("fruitStorage");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<String, Integer> map = (Map<String, Integer>) field.get(null);
        return map;
    }

    @Test
    void getReport_emptyStorage_returnsOnlyHeader() {
        String report = reportGenerator.getReport();
        assertEquals("fruit,quantity" + System.lineSeparator(), report);
    }

    @Test
    void getReport_withFruits_returnsCorrectReport() {
        Storage.setQuantity("apple", 10);
        Storage.setQuantity("banana", 5);

        String report = reportGenerator.getReport();

        String expectedHeader = "fruit,quantity" + System.lineSeparator();
        assertTrue(report.startsWith(expectedHeader));
        assertTrue(report.contains("apple,10"));
        assertTrue(report.contains("banana,5"));
    }

    @Test
    void getReport_afterAddingAndUpdatingFruits_returnsUpdatedReport() {
        Storage.setQuantity("apple", 10);
        Storage.addQuantity("apple", 5);
        Storage.setQuantity("orange", 7);

        String report = reportGenerator.getReport();

        assertTrue(report.contains("apple,15"));
        assertTrue(report.contains("orange,7"));
    }
}
