package core.basesyntax.service.reportgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.ShopDataBase;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new ReportGeneratorImpl();
    }

    @Test
    void testGenerateReport_Ok() {
        ShopDataBase.shopData.put("apple", 100);
        ShopDataBase.shopData.put("banana", 50);

        List<String> report = generator.generateReport();

        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("apple,100"));
        assertTrue(report.contains("banana,50"));
    }

    @Test
    void testGenerateEmptyReport_Ok() {
        List<String> report = generator.generateReport();
        assertEquals(1, report.size());
        assertEquals("fruit,quantity", report.get(0));
    }

    @AfterEach
    void tearDown() {
        ShopDataBase.shopData.clear();
    }

}
