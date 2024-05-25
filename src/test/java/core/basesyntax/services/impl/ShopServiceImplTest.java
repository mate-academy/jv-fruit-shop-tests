package core.basesyntax.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.services.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static List<FruitTransaction> fruitTransaction;
    private static List<FruitTransaction> fruitEmptyTransaction;
    private static ShopService shopService;
    private static Map<String, Integer> fruits;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 20),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 10),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 20),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 5),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 50));
        fruitEmptyTransaction = Collections.emptyList();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        shopService = new ShopServiceImpl(operationStrategy);
        fruits = Storage.fruits;
    }

    @Test
    void process_withNoEmptyTransactions_ok() {
        assertEquals(fruits.size(), 0);
        shopService.process(fruitTransaction);
        assertEquals(fruits.size(), 2);
        fruits.clear();
    }

    @Test
    void process_withEmptyTransaction_ok() {
        assertEquals(fruits.size(), 0);
        shopService.process(fruitEmptyTransaction);
        assertEquals(fruits.size(), 0);
    }
}
