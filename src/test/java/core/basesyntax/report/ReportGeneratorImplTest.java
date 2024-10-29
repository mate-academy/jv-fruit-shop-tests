package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;
    private ShopService shopService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
        reportGenerator = new ReportGeneratorImpl();
        reportGenerator.setShopService(shopService);
    }

    @Test
    void testGenerateReport_withFruits() {
        shopService.addFruits("apple", 100);
        shopService.addFruits("banana", 50);

        String report = reportGenerator.generateReport();
        String expectedReport = "Fruit, Quantity\nbanana,50\napple,100\n";

        assertEquals(expectedReport, report);
    }

    @Test
    void testGenerateReport_withoutFruits() {
        String report = reportGenerator.generateReport();
        String expectedReport = "Fruit, Quantity\n";

        assertEquals(expectedReport, report);
    }
}
