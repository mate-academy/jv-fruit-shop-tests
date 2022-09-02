package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandleTest {
    private static FruitTransaction transaction;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
    }

    @Before
    public void setUp() {
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 200);
        operationHandler.apply(transaction);
    }

    @Test
    public void balanceOperationHandler_putValidData_ok() {
        Integer expected = transaction.getQuantity();
        Integer actual = Storage.storage.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void balanceOperationHandler_putZeroQuantity_ok() {
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 0);
        operationHandler.apply(transaction);
        Integer expected = transaction.getQuantity();
        Integer actual = Storage.storage.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void balanceOperationHandler_replaceBalance_ok() {
        transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 300);
        operationHandler.apply(transaction);
        Integer expected = 300;
        Integer actual = Storage.storage.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
