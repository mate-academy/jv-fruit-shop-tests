package core.basesyntax.report;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.storage.FruitStorageImpl;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String APPLE = " apple";
    private static final String BANANA = " banana";
    private static final int ONE_HUNDRED_QUANTITY = 100;
    private static final int FIFTY_QUANTITY = 50;
    private FruitStorage fruitStorage;
    private ReportGenerator reportGenerator;
    private ShopService shopService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        fruitStorage = new FruitStorageImpl();
        shopService = new ShopServiceImpl(operationStrategy);
        reportGenerator = new ReportGeneratorImpl(fruitStorage);
    }

    @Test
    void generateReport_withFruits_success() {
        fruitStorage.addFruits(APPLE, ONE_HUNDRED_QUANTITY);
        fruitStorage.addFruits(BANANA, FIFTY_QUANTITY);

        String report = reportGenerator.generateReport();
        String expectedReport = "Fruit,Quantity" + System.lineSeparator()
                + " apple,100" + System.lineSeparator()
                + " banana,50" + System.lineSeparator();

        assertEquals(expectedReport, report);
    }

    @Test
    void generateReport_withoutFruits_success() {
        String report = reportGenerator.generateReport();
        String expectedReport = "Fruit,Quantity" + System.lineSeparator();

        assertEquals(expectedReport, report);
    }
}
