package core.basesyntax.service.reportgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

        List<String> expected = List.of(
                "fruit,quantity",
                "banana,50",
                "apple,100"

        );

        List<String> actual = generator.generateReport();
        assertEquals(expected, actual);
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
