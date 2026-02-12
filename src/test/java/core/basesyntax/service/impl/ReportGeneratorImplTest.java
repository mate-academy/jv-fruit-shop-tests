package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransfer;
import core.basesyntax.models.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import core.basesyntax.strategy.ReturnHandler;
import core.basesyntax.strategy.SupplyHandler;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private static final String PEAR = "pear";
    private static final String TITLE = "fruit,quantity";
    private static final String DELIMITER = ",";
    private static final Map<Operation, OperationHandler> HANDLERS
            = Map.of(Operation.BALANCE, new BalanceHandler(),
            Operation.PURCHASE, new PurchaseHandler(),
            Operation.RETURN, new ReturnHandler(),
            Operation.SUPPLY, new SupplyHandler());
    private static ReportGenerator reportGenerator;
    private static OperationStrategy operationStrategy;
    private static ShopService shopService;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
        operationStrategy = new OperationStrategyImpl(HANDLERS);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @BeforeEach
    void setUp() {
        Storage.getStoredFruits().clear();
    }

    @AfterEach
    void tearDown() {
        Storage.getStoredFruits().clear();
    }

    @Test
    void getReport_StorageOneFruit_Ok() {
        List<FruitTransfer> fruitTransferList
                = List.of(new FruitTransfer(Operation.BALANCE, PEAR, 50));
        shopService.process(fruitTransferList);
        String expected = TITLE + System.lineSeparator()
                + PEAR + DELIMITER + 50 + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected,actual);
    }

    @Test
    void getReport_StorageAfterAllOperations_Ok() {
        List<FruitTransfer> fruitTransferList
                = List.of(new FruitTransfer(Operation.BALANCE, PEAR, 50),
                        new FruitTransfer(Operation.SUPPLY, PEAR, 40),
                        new FruitTransfer(Operation.RETURN, PEAR, 5),
                        new FruitTransfer(Operation.PURCHASE, PEAR, 20));
        shopService.process(fruitTransferList);
        String expected = TITLE + System.lineSeparator()
                + PEAR + DELIMITER + 75 + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }

    @Test
    void getReport_EmptyStorage_Ok() {
        String expected = TITLE + System.lineSeparator();
        String actual = reportGenerator.getReport();
        assertEquals(expected, actual);
    }
}
