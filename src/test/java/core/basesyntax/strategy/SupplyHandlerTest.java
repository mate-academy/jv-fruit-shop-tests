package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static OperationHandler handler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        handler = new SupplyHandler();
        fruitTransaction = new FruitTransaction();
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test(expected = RuntimeException.class)
    public void operate_null_NotOk() {
        handler.operate(null);
    }

    @Test
    public void operate_Ok() {
        Storage.fruits.put("apple", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(10);
        handler.operate(fruitTransaction);
        Integer expected = 20;
        Integer actual = Storage.fruits.get("apple");
        Assert.assertEquals(expected, actual);
    }
}
