package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.Test;

public class PurchaseHandlerTest {
    private OperationHandler operationHandler = new PurchaseHandler();
    private FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    public void apply_correctData_ok() {
        Storage.storageFruits.put("banana", 400);
        fruitTransaction.setOperation(Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(200);
        operationHandler.apply(fruitTransaction);
        int actual = Storage.getStorage().get("banana");
        int expected = EXPECTED_FRUIT_QUANTITY;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperationHandler_NotEnoughFruit_NotOk() {
        Storage.storageFruits.put("banana", 10);
        fruitTransaction.setOperation(Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        operationHandler.apply(fruitTransaction);
    }
}
