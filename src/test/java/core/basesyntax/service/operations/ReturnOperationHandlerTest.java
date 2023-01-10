package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperation;

    @Before
    public void before() {
        returnOperation = new ReturnOperationHandler();
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerException_Balance() {
        returnOperation.handle(null);
    }

    @Test
    public void purchaseOperationHandler_OK() {
        Storage.fruits.put("apple", 10);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE,
                "apple", 5);
        Integer excepted = Storage.fruits.get(fruitTransaction.getFruitName())
                + fruitTransaction.getQuantity();
        returnOperation.handle(fruitTransaction);
        Integer quantityActual = Storage.fruits.get(fruitTransaction.getFruitName());
        Assert.assertEquals(quantityActual,excepted);
    }

    @Test
    public void purchaseOperationHandler_NotOK() {
        Storage.fruits.put("apple", 10);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.PURCHASE,
                "apple", 5);
        Integer excepted = Storage.fruits.get(fruitTransaction.getFruitName())
                - fruitTransaction.getQuantity();
        returnOperation.handle(fruitTransaction);
        Integer quantityActual = Storage.fruits.get(fruitTransaction.getFruitName());
        Assert.assertNotEquals(quantityActual,excepted);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
