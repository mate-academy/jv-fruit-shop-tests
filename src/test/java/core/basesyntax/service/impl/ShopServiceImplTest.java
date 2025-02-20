package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static ShopService shopService;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandler;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    public static void setUp() {
        operationHandler = new HashMap<>();
        operationHandler.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandler.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandler.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandler.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        operationStrategy = new OperationStrategyImpl(operationHandler);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterAll
    public static void clearStorage() {
        Storage.storage.clear();
    }

    @Test
    public void additionToStorageWithTheCorrectOperation_Ok() {
        List<FruitTransaction> list = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        10));

        shopService.process(list);

        int expectedValue = 10;

        boolean actualKey = Storage.storage.containsKey("banana");
        int actualValue = Storage.storage.get("banana");

        assertEquals(expectedValue, actualValue);
        assertTrue(actualKey);
    }
}
