package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseOperationHandlerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private OperationHandler handler;

    @Before
    public void beforEachTest() {
        handler = new PurchaseOperationHandler();
    }

    @Test
    public void purcheseOperationHandler_correctData_ok() {
        Storage.getStorage().put("apple", 100);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHES, "apple", 100);
        handler.apply(fruitTransaction);
        int amountAfter = Storage.getStorage().get("apple");
        Assert.assertEquals(0, amountAfter);
    }

    @Test
    public void purcheseOperationHandler_notEnoughFruits_notOk() {
        Storage.getStorage().put("apple",50);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHES, "apple", 100);
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("The amount of fruits less to sell");
        handler.apply(fruitTransaction);
    }

    @After
    public void afterEachTest() {
        Storage.getStorage().clear();
    }
}
