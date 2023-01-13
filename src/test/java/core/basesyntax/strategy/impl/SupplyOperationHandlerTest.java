package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private final SupplyOperationHandler supplyOperationHandler = new SupplyOperationHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    public void operate_validData_ok() {
        Storage.FRUITS_MAP.put("cherry", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("cherry");
        fruitTransaction.setQuantity(15);
        supplyOperationHandler.operate(fruitTransaction);
        Integer actual = Storage.FRUITS_MAP.get("cherry");
        Integer expected = 25;
        assertEquals("Wrong result of SUPPLY operation for input " + actual,expected, actual);
    }
}
