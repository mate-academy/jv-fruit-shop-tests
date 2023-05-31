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

public class SupplyTest {
    private static Supply supply;
    private static TransactionHandler transactionHandler;
    private static Fruit apple;
    private static Transaction appleSupplyTransaction;

    @BeforeClass
    public static void setUp() {
        supply = new Supply();
        transactionHandler = new TransactionHandler();
        apple = new Fruit("apple");
        appleSupplyTransaction = new Transaction();
        appleSupplyTransaction.setFruit(apple);
        appleSupplyTransaction.setOperation(Transaction.Operation.SUPPLY);
        appleSupplyTransaction.setSum(25);
    }

    @Before
    public void beforeAll() {
        Storage.storage.remove(apple);
    }

    @Test
    public void supply_addedNewFruit_ok() {
        supply.apply(transactionHandler, appleSupplyTransaction);
        Assert.assertEquals(appleSupplyTransaction.getSum(), Storage.storage.get(apple));
    }

    @Test
    public void supply_addedSumToExisted_ok() {
        Storage.storage.put(apple, 200);
        Integer expectedSum = Storage.storage.get(apple) + appleSupplyTransaction.getSum();
        supply.apply(transactionHandler, appleSupplyTransaction);
        Assert.assertEquals(expectedSum, Storage.storage.get(apple));
    }

    @Test
    public void supply_incorrectData_notOk() {
        Integer expectedSum = 0;
        supply.apply(transactionHandler, appleSupplyTransaction);
        Assert.assertNotEquals(expectedSum, Storage.storage.get(apple));
    }

    @Test
    public void supply_applyTwice_notOk() {
        Integer expectedSum = Storage.storage.getOrDefault(apple, 0)
                + appleSupplyTransaction.getSum();
        supply.apply(transactionHandler, appleSupplyTransaction);
        supply.apply(transactionHandler, appleSupplyTransaction);
        Assert.assertNotEquals(expectedSum, Storage.storage.get(apple));
    }

    @AfterClass
    public static void clearStorage() {
        Storage.storage.clear();
    }
}
