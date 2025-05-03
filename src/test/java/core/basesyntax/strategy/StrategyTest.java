package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationService;
import core.basesyntax.service.impl.BalanceServiceImpl;
import core.basesyntax.service.impl.PurchaseServiceImpl;
import core.basesyntax.service.impl.ReturnServiceImpl;
import core.basesyntax.service.impl.SupplyServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StrategyTest {
    private static final String BALANCE_OPERATION_CODE = "b";
    private static final String SUPPLY_OPERATION_CODE = "s";
    private static final String RETURN_OPERATION_CODE = "r";
    private static final String PURCHASE_OPERATION_CODE = "p";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String PINEAPPLE = "pineapple";
    private static final String PEACH = "peach";
    private static final int APPLE_NUMBER = 56;
    private static final int BANANA_NUMBER = 43;
    private static final int PINEAPPLE_NUMBER = 98;
    private static final int PEACH_NUMBER = 12;
    private static Storage storage;
    private static Strategy strategy;

    @BeforeAll
    public static void init() {
        storage = new Storage();
        Map<String, Integer> data = storage.getData();
        data.put(APPLE, APPLE_NUMBER);
        data.put(BANANA, BANANA_NUMBER);
        data.put(PINEAPPLE, PINEAPPLE_NUMBER);
        data.put(PEACH, PEACH_NUMBER);

        Map<FruitTransaction.Operation, OperationService> operationStrategies = new HashMap<>();
        operationStrategies.put(FruitTransaction.Operation.BALANCE,
                new BalanceServiceImpl(storage));
        operationStrategies.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseServiceImpl(storage));
        operationStrategies.put(FruitTransaction.Operation.RETURN,
                new ReturnServiceImpl(storage));
        operationStrategies.put(FruitTransaction.Operation.SUPPLY,
                new SupplyServiceImpl(storage));

        strategy = new Strategy(operationStrategies);
    }

    @Test
    public void doOperation_balanceFruitTransaction_OK() {
        Map<String, Integer> data = storage.getData();
        String fruitName = "coconut";
        int quantity = 5;
        Assertions.assertFalse(data.containsKey(fruitName));
        FruitTransaction balanceTransaction
                = new FruitTransaction(BALANCE_OPERATION_CODE, fruitName, quantity);
        strategy.doOperation(balanceTransaction);
        Assertions.assertTrue(data.containsKey(fruitName));
        Assertions.assertEquals(quantity, data.get(fruitName));
    }

    @Test
    public void doOperation_supplyFruitTransaction_OK() {
        Map<String, Integer> data = storage.getData();
        String fruitName = "kiwi";
        int quantity = 10;
        Assertions.assertFalse(data.containsKey(fruitName));
        FruitTransaction supplyTransaction1
                = new FruitTransaction(SUPPLY_OPERATION_CODE, fruitName, quantity);
        strategy.doOperation(supplyTransaction1);
        Assertions.assertTrue(data.containsKey(fruitName));
        Assertions.assertEquals(quantity, data.get(fruitName));

        fruitName = BANANA;
        quantity = 19;
        Assertions.assertTrue(data.containsKey(fruitName));
        FruitTransaction supplyTransaction2
                = new FruitTransaction(SUPPLY_OPERATION_CODE, fruitName, quantity);
        strategy.doOperation(supplyTransaction2);
        Assertions.assertTrue(data.containsKey(fruitName));
        Assertions.assertEquals(quantity + BANANA_NUMBER, data.get(fruitName));

    }

    @Test
    public void doOperation_purchaseFruitTransaction_OK() {
        Map<String, Integer> data = storage.getData();
        String fruitName = PINEAPPLE;
        int quantity = 32;
        Assertions.assertTrue(data.containsKey(fruitName));
        FruitTransaction purchaseTransaction
                = new FruitTransaction(PURCHASE_OPERATION_CODE, fruitName, quantity);
        strategy.doOperation(purchaseTransaction);
        Assertions.assertTrue(data.containsKey(fruitName));
        Assertions.assertEquals(PINEAPPLE_NUMBER - quantity, data.get(fruitName));
    }

    @Test
    public void doOperation_returnFruitTransaction_OK() {
        Map<String, Integer> data = storage.getData();
        String fruitName = PEACH;
        int quantity = 91;
        Assertions.assertTrue(data.containsKey(fruitName));
        FruitTransaction returnTransaction
                = new FruitTransaction(RETURN_OPERATION_CODE, fruitName, quantity);
        strategy.doOperation(returnTransaction);
        Assertions.assertTrue(data.containsKey(fruitName));
        Assertions.assertEquals(PEACH_NUMBER + quantity, data.get(fruitName));

        fruitName = "lemon";
        quantity = 2;
        Assertions.assertFalse(data.containsKey(fruitName));
        FruitTransaction returnTransaction1
                = new FruitTransaction(RETURN_OPERATION_CODE, fruitName, quantity);
        strategy.doOperation(returnTransaction1);
        Assertions.assertTrue(data.containsKey(fruitName));
        Assertions.assertEquals(quantity, data.get(fruitName));
    }
}
