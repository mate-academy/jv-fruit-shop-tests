package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private final ReturnOperationHandler returnOperationHandler = new ReturnOperationHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    public void operate_validData_ok() {
        Storage.FRUITS_MAP.put("cherry", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setFruit("cherry");
        fruitTransaction.setQuantity(15);
        returnOperationHandler.operate(fruitTransaction);
        Integer actual = Storage.FRUITS_MAP.get("cherry");
        Integer expected = 25;
        assertEquals("Wrong result of RETURN operation for input " + actual, expected, actual);
    }
}
