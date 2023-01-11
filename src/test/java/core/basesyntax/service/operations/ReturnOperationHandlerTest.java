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

    @Test
    public void returnOperationHandler_OK() {
        Storage.fruits.put("apple", 10);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN,
                "apple", 5);
        Integer excepted = Storage.fruits.get(fruitTransaction.getFruitName())
                + fruitTransaction.getQuantity();
        returnOperation.handle(fruitTransaction);
        Integer quantityActual = Storage.fruits.get(fruitTransaction.getFruitName());
        Assert.assertEquals(quantityActual,excepted);
    }

    @Test
    public void returnOperationHandler_NotOK() {
        Storage.fruits.put("apple", 10);
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN,
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
