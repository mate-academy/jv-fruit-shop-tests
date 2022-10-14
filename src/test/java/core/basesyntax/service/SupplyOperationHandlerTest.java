package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private OperationHandler handler = new SupplyOperationHandler();

    @Before
    public void afterEachTest() {
        Storage.getStorage().clear();
    }

    @Test
    public void supplyOperationHandler_correctData_Ok() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPLLY, "apple", 100);
        handler.apply(fruitTransaction);
        int amountAfter = Storage.getStorage().get("apple");
        Assert.assertEquals(100, amountAfter);
    }
}
