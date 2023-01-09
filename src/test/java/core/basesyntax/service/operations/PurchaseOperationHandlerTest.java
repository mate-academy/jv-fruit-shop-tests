package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseOperationHandlerTest {
    private static OperationHandler handler;
    private static FruitTransaction fruitTransaction;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        handler = new PurchaseOperationHandler();
        fruitTransaction = new FruitTransaction();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void balanceOperate_Ok() {
        Storage.fruits.put("banana", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(5);
        handler.operate(fruitTransaction);
        Integer expected = 5;
        Integer actual = Storage.fruits.get("banana");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void operate_QtyLessThanInStock_NotOk() {
        Storage.fruits.put("banana", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(15);
        exception.expect(RuntimeException.class);
        exception.expectMessage("Not enough "
                + fruitTransaction.getFruit() + " in shop storage. "
                + "Should be at least " + fruitTransaction.getQuantity()
                + " items. But storage has only "
                + Storage.fruits.get(fruitTransaction.getFruit()));
        handler.operate(fruitTransaction);
    }
}
