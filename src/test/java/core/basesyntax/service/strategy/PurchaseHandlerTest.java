package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static final int EXPECTED_FRUIT_QUANTITY = 200;
    private OperationHandler operationHandler = new PurchaseHandler();
    private FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    public void purchaseOperationHandler_CorrectData_Ok() {
        Storage.storageFruits.put("banana", 400);
        fruitTransaction.setOperation(Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(200);
        operationHandler.apply(fruitTransaction);
        int actual = Storage.getStorage().get("banana");
        int expected = EXPECTED_FRUIT_QUANTITY;
        assertEquals(expected, actual);
    }

    @Test
    public void purchaseOperationHandler_NotEnoughFruit_NotOk() {
        Storage.storageFruits.put("banana", 10);
        fruitTransaction.setOperation(Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        assertThrows(RuntimeException.class,
                () -> operationHandler.apply(fruitTransaction), "Not enough fruit on store");
    }
}
