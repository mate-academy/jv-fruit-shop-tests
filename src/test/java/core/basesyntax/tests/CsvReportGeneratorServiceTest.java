package core.basesyntax.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.db.ShopStorageImpl;
import core.basesyntax.service.impl.CsvReportGeneratorService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvReportGeneratorServiceTest {
    private CsvReportGeneratorService reportGeneratorService;
    private ShopStorage shopStorage;

    @BeforeEach
    public void setUp() {
        shopStorage = new ShopStorageImpl();
        reportGeneratorService = new CsvReportGeneratorService(shopStorage);
    }

    @Test
    public void generateReport_validInput_Ok() {
        shopStorage.updateQuantity("banana", 10);
        shopStorage.updateQuantity("apple", 5);

        List<String> report = reportGeneratorService.generateReport();

        assertEquals(3, report.size());
        assertEquals("fruit,quantity", report.get(0));
        assertTrue(report.contains("banana,10"));
        assertTrue(report.contains("apple,5"));
    }

    @Test
    public void generateReport_multipleFruitsAndQuantities_Ok() {
        shopStorage.updateQuantity("banana", 10);
        shopStorage.updateQuantity("apple", 5);
        shopStorage.updateQuantity("orange", 8);

        List<String> report = reportGeneratorService.generateReport();

        assertEquals(4, report.size());
        assertEquals("fruit,quantity", report.get(0));
        assertTrue(report.contains("banana,10"));
        assertTrue(report.contains("apple,5"));
        assertTrue(report.contains("orange,8"));
    }

    @Test
    public void generateReport_largeQuantityForFruit_Ok() {
        shopStorage.updateQuantity("banana", 1000);
        shopStorage.updateQuantity("apple", 500);

        List<String> report = reportGeneratorService.generateReport();

        assertEquals(3, report.size());
        assertEquals("fruit,quantity", report.get(0));
        assertTrue(report.contains("banana,1000"));
        assertTrue(report.contains("apple,500"));
    }

    @Test
    public void generateReport_zeroQuantityForFruit_Ok() {
        shopStorage.updateQuantity("banana", 0);
        shopStorage.updateQuantity("apple", 5);

        List<String> report = reportGeneratorService.generateReport();

        assertEquals(3, report.size());
        assertEquals("fruit,quantity", report.get(0));
        assertTrue(report.contains("banana,0"));
        assertTrue(report.contains("apple,5"));
    }

    @Test
    public void generateReport_emptyShopStorage_Ok() {
        List<String> report = reportGeneratorService.generateReport();

        assertEquals(1, report.size());
        assertEquals("fruit,quantity", report.get(0));
    }
}

