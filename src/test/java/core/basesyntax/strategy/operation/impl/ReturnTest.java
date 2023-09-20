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

public class ReturnTest {
    private static Return returnOperation;
    private static TransactionHandler transactionHandler;
    private static Fruit banana;
    private static Transaction bananaReturnTransaction;

    @BeforeClass
    public static void setUp() {
        returnOperation = new Return();
        transactionHandler = new TransactionHandler();
        banana = new Fruit("banana");
        bananaReturnTransaction = new Transaction();
        bananaReturnTransaction.setFruit(banana);
        bananaReturnTransaction.setOperation(Transaction.Operation.RETURN);
        bananaReturnTransaction.setSum(100);
    }

    @Before
    public void beforeAll() {
        Storage.storage.remove(banana);
    }

    @Test
    public void balance_addedNewFruit_ok() {
        returnOperation.apply(transactionHandler, bananaReturnTransaction);
        Assert.assertEquals(bananaReturnTransaction.getSum(), Storage.storage.get(banana));
    }

    @Test
    public void balance_addedSumToExisting_ok() {
        Storage.storage.put(banana, 200);
        Integer expectedSum = Storage.storage.get(banana) + bananaReturnTransaction.getSum();
        returnOperation.apply(transactionHandler, bananaReturnTransaction);
        Assert.assertEquals(expectedSum, Storage.storage.get(banana));
    }

    @Test
    public void return_incorrectData_notOk() {
        Integer expectedSum = 0;
        returnOperation.apply(transactionHandler, bananaReturnTransaction);
        Assert.assertNotEquals(expectedSum, Storage.storage.get(banana));
    }

    @Test
    public void return_applyTwice_notOk() {
        Integer expectedSum = Storage.storage.getOrDefault(banana, 0)
                + bananaReturnTransaction.getSum();
        returnOperation.apply(transactionHandler, bananaReturnTransaction);
        returnOperation.apply(transactionHandler, bananaReturnTransaction);
        Assert.assertNotEquals(expectedSum, Storage.storage.get(banana));
    }

    @AfterClass
    public static void clearStorage() {
        Storage.storage.clear();
    }
}
