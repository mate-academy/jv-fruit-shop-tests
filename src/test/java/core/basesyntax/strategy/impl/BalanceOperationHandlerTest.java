package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private final BalanceOperationHandler balanceOperationHandler = new BalanceOperationHandler();
    private final FruitTransaction fruitTransaction = new FruitTransaction();

    @Test
    public void operate_validData_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("cherry");
        fruitTransaction.setQuantity(15);
        balanceOperationHandler.operate(fruitTransaction);
        Integer actual = Storage.FRUITS_MAP.get("cherry");
        Integer expected = 15;
        assertEquals("Wrong result of BALANCE operation for input " + actual, expected, actual);
    }
}
