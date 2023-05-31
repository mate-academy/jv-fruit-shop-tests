package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.Test;

public class SupplyHandlerTest {
    private static final int EXPECTED_FRUIT_QUANTITY = 600;
    private OperationHandler operationHandler = new SupplyHandler();
    private FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    public void purchaseOperationHandler_CorrectData_Ok() {
        Storage.storageFruits.put("banana", 400);
        fruitTransaction.setOperation(Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(200);
        operationHandler.apply(fruitTransaction);
        int actual = Storage.getStorage().get("banana");
        int expected = EXPECTED_FRUIT_QUANTITY;
        assertEquals(expected, actual);
    }
}
