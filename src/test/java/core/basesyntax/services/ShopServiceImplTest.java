package core.basesyntax.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.data.FruitTransaction;
import core.basesyntax.storage.FruitStorage;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handlers.BalanceOperation;
import core.basesyntax.strategy.handlers.OperationHandler;
import core.basesyntax.strategy.handlers.PurchaseOperation;
import core.basesyntax.strategy.handlers.ReturnOperation;
import core.basesyntax.strategy.handlers.SupplyOperation;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String APPLE_TEST = "apple";
    private ShopServiceImpl shopService;
    private FruitStorage storage;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation()
        );
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        shopService = new ShopServiceImpl(operationStrategy);
        storage = new FruitStorage();
    }

    @Test
    void process_validInput_Ok() {
        List<FruitTransaction> testList = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE_TEST, 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE_TEST, 15),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, APPLE_TEST, 1),
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE_TEST, 4));
        shopService.process(testList);
        storage = new FruitStorage();
        int expected = 10;
        int actual = storage.getAmount(APPLE_TEST);
        assertEquals(expected, actual, "Expected 10, but got: " + actual);
    }

    @AfterEach
    void afterEach() {
        storage.clear();
    }
}
