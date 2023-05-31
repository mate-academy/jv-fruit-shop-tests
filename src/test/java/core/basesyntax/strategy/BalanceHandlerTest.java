package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler handler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        handler = new BalanceHandler();
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
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(10);
        handler.operate(fruitTransaction);
        Integer expected = 10;
        Integer actual = Storage.fruits.get("apple");
        Assert.assertEquals(expected, actual);
    }
}
