package core.basesyntax.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;
    private static FruitTransaction transaction;

    @BeforeClass
    public static void before() {
        returnOperationHandler = new ReturnOperationHandler();
        transaction = new FruitTransaction(Operation.RETURN,"apple",25);
    }

    @Test
    public void validReturnOperationHandler_Ok() {
        Storage.fruits.put("apple", 35);
        Integer expected = Storage.fruits.get(transaction.getFruit()) + transaction.getQuantity();
        returnOperationHandler.handle(transaction);
        Integer actual = Storage.fruits.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void noValidReturnOperationHandler_NotOk() {
        Storage.fruits.put("apple", 35);
        Integer expected = Storage.fruits.get(transaction.getFruit()) - transaction.getQuantity();
        returnOperationHandler.handle(transaction);
        Integer actual = Storage.fruits.get(transaction.getFruit());
        Assert.assertNotEquals(expected, actual);
    }

    @After
    public void cleanAfter() {
        Storage.fruits.clear();
    }
}
