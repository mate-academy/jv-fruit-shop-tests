package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;
    private ShopService shopService;
    private OperationStrategy operationStrategy;
    private final String lineSeparator = System.lineSeparator();

    @BeforeEach
    void setUp() {
        shopService = new ShopServiceImpl(operationStrategy);
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void testGenerateReport_withFruits() {
        shopService.addFruits("apple", 100);
        shopService.addFruits("banana", 50);

        String report = reportGenerator.getReport(shopService);
        String expectedReport = "fruit,quantity" + lineSeparator
                + "banana,50" + lineSeparator + "apple,100";

        assertEquals(expectedReport, report);
    }

    @Test
    void testGenerateReport_withoutFruits() {
        String report = reportGenerator.getReport(shopService);
        String expectedReport = "fruit,quantity" + lineSeparator;

        assertEquals(expectedReport, report);
    }
}
