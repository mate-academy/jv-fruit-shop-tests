package core.basesyntax.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static FruitTransaction transaction;

    @BeforeClass
    public static void before() {
        operationHandler = new BalanceOperationHandler();
        transaction = new FruitTransaction(Operation.BALANCE,"apple", 100);
    }

    @Test
    public void correctBalance_Ok() {
        operationHandler.handle(transaction);
        Integer expected = transaction.getQuantity();
        Integer actual = Storage.fruits.get("apple");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notMatchesNameOfFruit_NotOk() {
        operationHandler.handle(transaction);
        Integer expected = transaction.getQuantity();
        Integer actual = Storage.fruits.get("banana");
        Assert.assertNotEquals(expected, actual);
    }

    @AfterClass
    public static void cleanAfter() {
        Storage.fruits.clear();
    }
}
