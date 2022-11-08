package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.TransactionHandler;
import core.basesyntax.strategy.impl.BalanceTransactionHandler;
import core.basesyntax.strategy.impl.PurchaseTransactionHandler;
import core.basesyntax.strategy.impl.ReturnTransactionHandler;
import core.basesyntax.strategy.impl.SupplyTransactionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceImplTest {
    private static Fruit fruit;
    private static ShopService shopService;
    private static List<FruitTransaction> testFruitTransactions;
    private static Map<Fruit, Integer> expected;

    @BeforeClass
    public static void beforeClass() {
        fruit = new Fruit("banana");
        expected = new HashMap<>();
        shopService = new ShopServiceImpl(getOperationHandlerMap());
    }

    @Before
    public void setUp() {
        testFruitTransactions = new ArrayList<>();
    }

    @Test
    public void processData_validData_ok() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, 50);
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, fruit, 10);
        FruitTransaction purchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, fruit, 20);
        FruitTransaction returnTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, fruit, 5);
        testFruitTransactions.add(balanceTransaction);
        testFruitTransactions.add(supplyTransaction);
        testFruitTransactions.add(purchaseTransaction);
        testFruitTransactions.add(returnTransaction);
        expected.put(fruit, 45);
        shopService.processData(testFruitTransactions);
        Map<Fruit, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void processData_testBalance_ok() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, 22);
        testFruitTransactions.add(balanceTransaction);
        expected.put(fruit, 22);
        shopService.processData(testFruitTransactions);
        Map<Fruit, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void processData_testSupply_ok() {
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, fruit, 10);
        testFruitTransactions.add(supplyTransaction);
        expected.put(fruit, 10);
        shopService.processData(testFruitTransactions);
        Map<Fruit, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test
    public void processData_testPurchase_ok() {
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, 22);
        FruitTransaction purchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, fruit, 5);
        testFruitTransactions.add(balanceTransaction);
        testFruitTransactions.add(purchaseTransaction);
        expected.put(fruit, 17);
        shopService.processData(testFruitTransactions);
        Map<Fruit, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void processData_testPurchase_emptyBalance_notOk() {
        FruitTransaction purchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, fruit, 5);
        testFruitTransactions.add(purchaseTransaction);
        shopService.processData(testFruitTransactions);
    }

    @Test
    public void processData_testReturn_ok() {
        FruitTransaction returnTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, fruit, 7);
        testFruitTransactions.add(returnTransaction);
        expected.put(fruit, 7);
        shopService.processData(testFruitTransactions);
        Map<Fruit, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    private static Map<FruitTransaction.Operation, TransactionHandler> getOperationHandlerMap() {
        Map<FruitTransaction.Operation, TransactionHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnTransactionHandler());
        return operationHandlerMap;
    }
}
