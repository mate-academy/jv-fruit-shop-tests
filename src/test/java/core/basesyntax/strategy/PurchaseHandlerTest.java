package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static OperationHandler handler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        handler = new PurchaseHandler();
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
        Storage.fruits.put("banana", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(5);
        handler.operate(fruitTransaction);
        Integer expected = 5;
        Integer actual = Storage.fruits.get("banana");
        Assert.assertEquals(expected, actual);
    }
}
