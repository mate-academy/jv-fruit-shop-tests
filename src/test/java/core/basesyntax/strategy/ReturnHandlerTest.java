package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static OperationHandler handler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        handler = new ReturnHandler();
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
    public void operate_Ok() {
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
