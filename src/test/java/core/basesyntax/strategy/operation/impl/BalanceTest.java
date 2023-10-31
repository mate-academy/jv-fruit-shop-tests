package core.basesyntax.strategy.operation.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.operation.util.TransactionHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceTest {
    private static Balance balance;
    private static TransactionHandler transactionHandler;
    private static Fruit apple;
    private static Transaction appleBalanceTransaction;

    @BeforeClass
    public static void setUp() {
        balance = new Balance();
        transactionHandler = new TransactionHandler();
        apple = new Fruit("apple");
        appleBalanceTransaction = new Transaction();
        appleBalanceTransaction.setFruit(apple);
        appleBalanceTransaction.setOperation(Transaction.Operation.BALANCE);
        appleBalanceTransaction.setSum(40);
    }

    @Before
    public void beforeAll() {
        Storage.storage.remove(apple);
    }

    @Test
    public void balance_addedNewFruit_ok() {
        balance.apply(transactionHandler, appleBalanceTransaction);
        Assert.assertEquals(appleBalanceTransaction.getSum(), Storage.storage.get(apple));
    }

    @Test
    public void balance_addedSumToExisting_ok() {
        Storage.storage.put(apple, 60);
        Integer expectedSum = Storage.storage.get(apple) + appleBalanceTransaction.getSum();
        balance.apply(transactionHandler, appleBalanceTransaction);
        Assert.assertEquals(expectedSum, Storage.storage.get(apple));
    }

    @Test
    public void balance_incorrectData_notOk() {
        Integer expectedSum = 0;
        balance.apply(transactionHandler, appleBalanceTransaction);
        Assert.assertNotEquals(expectedSum, Storage.storage.get(apple));
    }

    @Test
    public void balance_applyTwice_notOk() {
        Integer expectedSum = appleBalanceTransaction.getSum();
        balance.apply(transactionHandler, appleBalanceTransaction);
        balance.apply(transactionHandler, appleBalanceTransaction);
        Assert.assertNotEquals(expectedSum, Storage.storage.get(apple));
    }

    @AfterClass
    public static void clearStorage() {
        Storage.storage.clear();
    }
}
