package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.QuantityException;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private PurchaseOperationHandler purchaseOperationHandler;

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        purchaseOperationHandler = new PurchaseOperationHandler();
        Storage.fruits.put("banana", 16);
    }

    @Test
    public void handle_rightAction_Ok() {
        FruitTransaction item = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 15);
        purchaseOperationHandler.handle(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 1);
    }

    @Test
    public void handle_purchaseExceed_notOk() {
        FruitTransaction item = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 17);
        Assertions.assertThrows(QuantityException.class, () ->
                purchaseOperationHandler.handle(item));
    }
}
