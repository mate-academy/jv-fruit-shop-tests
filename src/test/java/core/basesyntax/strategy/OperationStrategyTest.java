package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.handler.BalanceOperationHandler;
import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.PurchaseOperationHandler;
import core.basesyntax.handler.ReturnOperationHandler;
import core.basesyntax.handler.SupplyOperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationStrategy;
    private static Map<Operation, OperationHandler> operationHandlerMap;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = Map.of(
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.PURCHASE, new PurchaseOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler(),
                Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategy(operationHandlerMap);
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void handleOperation_balanceOperation_ok() {
        fruitTransaction.setOperation(Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        operationStrategy.handleOperation(fruitTransaction);
        Map<String, Integer> expectedMap = Map.of("banana", 20);
        Assertions.assertEquals(expectedMap, Storage.FRUITS);
    }

    @Test
    void handleOperation_purchaseOperation_ok() {
        Storage.FRUITS.put("banana", 20);
        fruitTransaction.setOperation(Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        operationStrategy.handleOperation(fruitTransaction);
        Map<String, Integer> expectedMap = Map.of("banana", 0);
        Assertions.assertEquals(expectedMap, Storage.FRUITS);
    }

    @Test
    void handleOperation_returnOperation_ok() {
        Storage.FRUITS.put("banana", 20);
        fruitTransaction.setOperation(Operation.RETURN);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        operationStrategy.handleOperation(fruitTransaction);
        Map<String, Integer> expectedMap = Map.of("banana", 40);
        Assertions.assertEquals(expectedMap, Storage.FRUITS);
    }

    @Test
    void handleOperation_supplyOperation_ok() {
        Storage.FRUITS.put("banana", 20);
        fruitTransaction.setOperation(Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(30);
        operationStrategy.handleOperation(fruitTransaction);
        Map<String, Integer> expectedMap = Map.of("banana", 50);
        Assertions.assertEquals(expectedMap, Storage.FRUITS);
    }

    @Test
    void handleOperation_nullOperation_notOk() {
        Storage.FRUITS.put("banana", 20);
        fruitTransaction.setOperation(null);
        fruitTransaction.setFruit("banana");
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.handleOperation(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}
