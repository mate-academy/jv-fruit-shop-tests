package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exeption.InvalidData;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationStrategy operationStrategy;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        Storage.FRUITS_MAP.put("cherry", 10);
        operationStrategy = new OperationStrategyImpl();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("cherry");
        fruitTransaction.setQuantity(15);
    }

    @Test (expected = InvalidData.class)
    public void operate_invalidData() {
        operationStrategy.getOperation(FruitTransaction.Operation.PURCHASE)
                .operate(fruitTransaction);
    }

    @Test
    public void operate_validData_ok() {
        fruitTransaction.setQuantity(9);
        operationStrategy.getOperation(FruitTransaction.Operation.PURCHASE)
                .operate(fruitTransaction);
        Integer actual = Storage.FRUITS_MAP.get("cherry");
        Integer expected = 1;
        assertEquals("Wrong result of PURCHASE operation for input " + actual, expected, actual);
    }
}
