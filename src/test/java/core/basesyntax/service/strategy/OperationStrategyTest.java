package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static OperationStrategy operationStrategy;
    private static StorageDao storageDao;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        operationHandlerMap = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(storageDao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(storageDao),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler(storageDao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(storageDao));
        operationStrategy = new OperationStrategy(operationHandlerMap);
        fruitTransaction = new FruitTransaction();
    }

    @Test
    void handleOperation_balanceOperation_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(APPLE);
        fruitTransaction.setQuantity(40);
        operationStrategy.processOperation(fruitTransaction);
        Map<String, Integer> expectedMap = Map.of(APPLE, 40);
        assertEquals(expectedMap, Storage.FRUITS);
    }

    @Test
    void handleOperation_purchaseOperation_ok() {
        Storage.FRUITS.put(APPLE, 20);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(APPLE);
        fruitTransaction.setQuantity(10);
        operationStrategy.processOperation(fruitTransaction);
        Map<String, Integer> expectedMap = Map.of(APPLE, 10);
        assertEquals(expectedMap, Storage.FRUITS);
    }

    @Test
    void handleOperation_returnOperation_ok() {
        Storage.FRUITS.put(BANANA, 20);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(20);
        operationStrategy.processOperation(fruitTransaction);
        Map<String, Integer> expectedMap = Map.of(BANANA, 40);
        assertEquals(expectedMap, Storage.FRUITS);
    }

    @Test
    void handleOperation_supplyOperation_ok() {
        Storage.FRUITS.put(BANANA, 20);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(30);
        operationStrategy.processOperation(fruitTransaction);
        Map<String, Integer> expectedMap = Map.of(BANANA, 50);
        assertEquals(expectedMap, Storage.FRUITS);
    }

    @Test
    void handleOperation_nullOperation_notOk() {
        Storage.FRUITS.put(BANANA, 20);
        fruitTransaction.setOperation(null);
        fruitTransaction.setFruit(BANANA);
        assertThrows(RuntimeException.class,
                () -> operationStrategy.processOperation(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}
