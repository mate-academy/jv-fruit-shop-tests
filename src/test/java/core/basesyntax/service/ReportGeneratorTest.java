package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {

    private ReportGenerator reportGenerator;
    private FruitDB fruitDB;

    @BeforeEach
    void setUp() {
        fruitDB = new FruitDB();
        reportGenerator = new ReportGenerator(fruitDB);
    }

    @Test
    void generateReport_withValidData_returnsCorrectReport() {
        fruitDB.add("apple", 50);
        fruitDB.add("banana", 30);
        List<String> report = reportGenerator.generateReport();
        assertEquals(3, report.size());
        assertEquals("Fruit,Quantity", report.get(0));
        assertTrue(report.contains("apple,50"));
        assertTrue(report.contains("banana,30"));
    }

    @Test
    void generateReport_emptyInventory_returnsOnlyHeader() {
        List<String> report = reportGenerator.generateReport();
        assertEquals(1, report.size());
        assertEquals("Fruit,Quantity", report.get(0));
    }

    @Test
    void generateReport_withNegativeQuantities_includesNegativeValues() {
        fruitDB.add("apple", -10);
        List<String> report = reportGenerator.generateReport();
        assertEquals(2, report.size());
        assertEquals("Fruit,Quantity", report.get(0));
        assertTrue(report.contains("apple,-10"));
    }

    @Test
    void generateReport_withZeroQuantity_includesZeroValues() {
        fruitDB.add("apple", 0);
        List<String> report = reportGenerator.generateReport();
        assertEquals(2, report.size());
        assertEquals("Fruit,Quantity", report.get(0));
        assertTrue(report.contains("apple,0"));
    }

    @Test
    void generateReport_withMultipleFruits_sortsAlphabetically() {
        fruitDB.add("banana", 30);
        fruitDB.add("apple", 50);
        fruitDB.add("cherry", 20);
        List<String> report = reportGenerator.generateReport();
        assertEquals(4, report.size());
        assertEquals("Fruit,Quantity", report.get(0));
        assertEquals("apple,50", report.get(1));
        assertEquals("banana,30", report.get(2));
        assertEquals("cherry,20", report.get(3));
    }
}
