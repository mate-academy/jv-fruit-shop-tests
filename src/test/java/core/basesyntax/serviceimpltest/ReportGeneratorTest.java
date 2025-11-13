package core.basesyntax.serviceimpltest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.database.FruitStock;
import core.basesyntax.serviceimpl.ReportGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportGeneratorTest {

    @BeforeEach
    void setUp() {
        FruitStock.stock.clear();
    }

    @Test
    void generateReport_validStock_shouldReturnCorrectReport() {
        FruitStock fruitStock = new FruitStock();
        fruitStock.add("apple", 10);
        fruitStock.add("banana", 5);
        ReportGenerator reportGenerator = new ReportGenerator(fruitStock);
        String report = reportGenerator.generateReport();
        assertTrue(report.startsWith("fruit,quantity"), "No header");
        assertTrue(report.contains("apple,10"), "Missing apple");
        assertTrue(report.contains("banana,5"), "Missing banana");
    }

    @Test
    void generateReport_emptyStock_shouldReturnOnlyHeader() {
        FruitStock fruitStock = new FruitStock();
        ReportGenerator reportGenerator = new ReportGenerator(fruitStock);
        String report = reportGenerator.generateReport();
        assertTrue(report.equals("fruit,quantity" + System.lineSeparator()),
                "Report should contain only header for empty stock");
    }
}
