package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exeption.InvalidData;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private final PurchaseOperationHandler purchaseOperationHandler
            = new PurchaseOperationHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Test (expected = InvalidData.class)
    public void operate_invalidData_notOk() {
        Storage.FRUITS_MAP.put("cherry", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("cherry");
        fruitTransaction.setQuantity(15);
        purchaseOperationHandler.operate(fruitTransaction);
    }

    @Test
    public void operate_validData_ok() {
        Storage.FRUITS_MAP.put("cherry", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("cherry");
        fruitTransaction.setQuantity(9);
        purchaseOperationHandler.operate(fruitTransaction);
        Integer actual = Storage.FRUITS_MAP.get("cherry");
        Integer expected = 1;
        assertEquals("Wrong result of PURCHASE operation for input " + actual, expected, actual);
    }
}
