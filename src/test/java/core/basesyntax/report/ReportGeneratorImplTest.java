package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final int ONE_HUNDREDL_QUANTITY = 100;
    private static final int FIFTY_QUANTITY = 50;
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
        shopService.addFruits(APPLE, ONE_HUNDREDL_QUANTITY);
        shopService.addFruits(BANANA, FIFTY_QUANTITY);

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
