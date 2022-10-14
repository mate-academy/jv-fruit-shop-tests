package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler handler = new PurchaseOperationHandler();

    @Test
    public void supplyOperationHandler_correctData_Ok() {
        Storage.getStorage().put("apple",100);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHES, "apple", 100);
        handler.apply(fruitTransaction);
        int amountAfter = Storage.getStorage().get("apple");
        Assert.assertEquals(0, amountAfter);
    }
}
