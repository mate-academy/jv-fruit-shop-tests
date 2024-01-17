package core.basesyntax.service.operationhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseHandlerTest {
    private PurchaseHandler purchaseHandler;

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        purchaseHandler = new PurchaseHandler();
        Storage.fruits.put("banana", 16);
    }

    @Test
    public void handle_rightAction_ok() {
        FruitTransaction item = new FruitTransaction(Operation.PURCHASE,
                "banana", 15);
        purchaseHandler.handleOperation(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertEquals(quantity, 1);
    }

    @Test
    public void handle_purchaseExceed_notOk() {
        FruitTransaction item = new FruitTransaction(Operation.PURCHASE,
                "banana", 17);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> purchaseHandler.handleOperation(item));
    }

    @Test
    public void handle_wrongAction_notOk() {
        FruitTransaction item = new FruitTransaction(Operation.PURCHASE,
                "banana", 15);
        purchaseHandler.handleOperation(item);
        int quantity = Storage.fruits.get("banana");
        Assertions.assertNotEquals(quantity, 14);
    }
}
