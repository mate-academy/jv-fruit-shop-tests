package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler handler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        handler = new ReturnOperationHandler();
        fruitTransaction = new FruitTransaction();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test(expected = RuntimeException.class)
    public void operate_null_NotOk() {
        handler.operate(null);
    }

    @Test
    public void returnOperate_Ok() {
        Storage.fruits.put("apple", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(5);
        handler.operate(fruitTransaction);
        Integer expected = 15;
        Integer actual = Storage.fruits.get("apple");
        Assert.assertEquals(expected, actual);
    }
}
