package core.basesyntax.service;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.operation.BalanceOperation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperation;
import core.basesyntax.operation.ReturnOperation;
import core.basesyntax.operation.SupplyOperation;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.strategy.StrategyImpl;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static List<FruitTransaction> fruitTransaction;
    private static List<FruitTransaction> fruitEmptyTransaction;
    private static ShopService shopService;
    private static Map<String, Integer> fruits;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = List.of(
                new FruitTransaction(Operation.BALANCE, BANANA, 20),
                new FruitTransaction(Operation.BALANCE, APPLE, 100),
                new FruitTransaction(Operation.SUPPLY, BANANA, 100),
                new FruitTransaction(Operation.PURCHASE, BANANA, 13),
                new FruitTransaction(Operation.RETURN, APPLE, 10),
                new FruitTransaction(Operation.PURCHASE, APPLE, 20),
                new FruitTransaction(Operation.PURCHASE, BANANA, 5),
                new FruitTransaction(Operation.SUPPLY, BANANA, 50));
        fruitEmptyTransaction = Collections.emptyList();

        Map<Operation, OperationHandler> operationHandlers = Map.of(
                Operation.BALANCE, new BalanceOperation(),
                Operation.PURCHASE, new PurchaseOperation(),
                Operation.RETURN, new ReturnOperation(),
                Operation.SUPPLY, new SupplyOperation());
        Strategy operationStrategy = new StrategyImpl(operationHandlers);
        shopService = new core.basesyntax.serviceimpl.ShopServiceImpl(operationStrategy);
        fruits = FruitStorage.getFruits();
    }

    @Test
    void process_noEmptyTransactions_ok() {
        Assertions.assertEquals(fruits.size(), 0);
        shopService.process(fruitTransaction);
        Assertions.assertEquals(fruits.size(), 2);
        FruitStorage.getFruits().clear();
    }

    @Test
    void process_emptyTransaction_ok() {
        Assertions.assertEquals(fruits.size(), 0);
        shopService.process(fruitEmptyTransaction);
        Assertions.assertEquals(fruits.size(), 0);
    }
}
