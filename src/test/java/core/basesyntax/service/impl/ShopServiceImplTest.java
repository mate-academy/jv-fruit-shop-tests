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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShopServiceImplTest {
    private static final Map<Fruit, Integer> STORAGE = Storage.fruits;
    private static Fruit fruit;
    private static ShopService shopService;
    private static List<FruitTransaction> testFruitTransactions;
    private static Map<Fruit, Integer> expected;

    @BeforeClass
    public static void beforeClass() {
        fruit = new Fruit("banana");
        expected = new HashMap<>();
        Map<FruitTransaction.Operation, TransactionHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyTransactionHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseTransactionHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnTransactionHandler());
        shopService = new ShopServiceImpl(operationHandlerMap);
        FruitTransaction balanceTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, fruit, 50);
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, fruit, 10);
        FruitTransaction purchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, fruit, 20);
        FruitTransaction returnTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, fruit, 5);
        testFruitTransactions = List.of(balanceTransaction, supplyTransaction,
                purchaseTransaction, returnTransaction);
    }

    @Test
    public void processData_ok() {
        expected.put(fruit, 45);
        shopService.processData(testFruitTransactions);
        Map<Fruit, Integer> actual = STORAGE;
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void processData_isNull_notOk() {
        shopService.processData(null);
    }

    @AfterClass
    public static void afterClass() {
        STORAGE.clear();
    }
}
