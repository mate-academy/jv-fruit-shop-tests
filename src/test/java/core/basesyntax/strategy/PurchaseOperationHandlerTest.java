package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.QuantityException;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        Storage.fruits.clear();
        purchaseOperationHandler = new PurchaseOperationHandler();
        Storage.fruits.put("banana", 16);
    }

    @Test
    public void purchaseOperationHandler_handle_Ok() {
        FruitTransaction item = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 15);
        purchaseOperationHandler.handle(item);
        int quantity = Storage.fruits.get("banana");
        Assert.assertEquals(quantity, 1);
    }

    @Test(expected = QuantityException.class)
    public void purchaseOperationHandler_purchaseExceed_notOk() {
        FruitTransaction item = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 17);
        purchaseOperationHandler.handle(item);
    }
}
