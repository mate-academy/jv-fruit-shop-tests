package strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dao.FruitStorageDaoImpl;
import java.util.HashMap;
import java.util.Map;
import model.Fruit;
import model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StrategyTest {
    private FruitStorageDaoImpl fruitStorageDao;
    private BalanceOperationStrategy balanceOperationStrategy;
    private SupplyOperationStrategy supplyOperationStrategy;
    private PurchaseOperationStrategy purchaseOperationStrategy;
    private ReturnOperationStrategy returnOperationStrategy;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
        balanceOperationStrategy = new BalanceOperationStrategy();
        supplyOperationStrategy = new SupplyOperationStrategy();
        purchaseOperationStrategy = new PurchaseOperationStrategy();
        returnOperationStrategy = new ReturnOperationStrategy();
        operationStrategy = new OperationStrategyImpl();
        fruitStorageDao.set("banana", 15);
        fruitStorageDao.set("apple", 10);
    }

    @Test
    void handleBalanceOperation_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 15);
        expected.put("apple", 5);

        Fruit fruit = new Fruit("apple", 5);
        balanceOperationStrategy.handle(fruit, fruitStorageDao);

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "The quantity of existing fruit should remain unchanged after balance operation");
    }

    @Test
    void handleSupplyOperation_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 35);
        expected.put("apple", 10);

        Fruit fruit = new Fruit("banana", 20);
        supplyOperationStrategy.handle(fruit, fruitStorageDao);

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }

    @Test
    void handlePurchaseOperation_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 15);
        expected.put("apple", 3);

        Fruit fruit = new Fruit("apple", 7);
        purchaseOperationStrategy.handle(fruit, fruitStorageDao);

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }

    @Test
    void handleReturnOperation_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 15);
        expected.put("apple", 17);

        Fruit fruit = new Fruit("apple", 7);
        returnOperationStrategy.handle(fruit, fruitStorageDao);

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }

    @Test
    void getBalanceOperation_ok() {
        new BalanceOperationStrategy();
        OperationHandler handler;
        handler = operationStrategy.get(Operation.BALANCE);

        assertTrue(handler instanceof BalanceOperationStrategy,
                "The handler should be an instance of BalanceOperationStrategy");
    }

    @Test
    void getSupplyOperation_ok() {
        new SupplyOperationStrategy();
        OperationHandler handler;
        handler = operationStrategy.get(Operation.SUPPLY);

        assertTrue(handler instanceof SupplyOperationStrategy,
                "The handler should be an instance of SupplyOperationStrategy");
    }

    @Test
    void getPurchaseOperation_ok() {
        new PurchaseOperationStrategy();
        OperationHandler handler;
        handler = operationStrategy.get(Operation.PURCHASE);

        assertTrue(handler instanceof PurchaseOperationStrategy,
                "The handler should be an instance of PurchaseOperationStrategy");
    }

    @Test
    void getReturnOperation_ok() {
        new ReturnOperationStrategy();
        OperationHandler handler;
        handler = operationStrategy.get(Operation.RETURN);

        assertTrue(handler instanceof ReturnOperationStrategy,
                "The handler should be an instance of ReturnOperationStrategy");
    }
}
