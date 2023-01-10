package core.basesyntax.service.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {

    private static OperationHandler balanceOperation;

    @Before
    public void before() {
        balanceOperation = new BalanceOperationHandler();
    }

    @Test(expected = NullPointerException.class)
    public void nullPointerException_Balance() {
        balanceOperation.handle(null);
    }

    @Test
    public void balanceOperationHandler_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE,
                "apple", 10);
        Integer excepted = fruitTransaction.getQuantity();
        balanceOperation.handle(fruitTransaction);
        Integer quantityActual = Storage.fruits.get(fruitTransaction.getFruitName());
        Assert.assertEquals(quantityActual, excepted);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
