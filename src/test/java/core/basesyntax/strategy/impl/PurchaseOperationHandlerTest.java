package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void validClass() {
        assertEquals(operationStrategy
                        .getOperation(FruitTransaction.Operation.PURCHASE).getClass(),
                PurchaseOperationHandler.class);
    }

    @Test (expected = RuntimeException.class)
    public void invalidData() {
        operationStrategy.getOperation(FruitTransaction.Operation.PURCHASE)
                .operate(fruitTransaction);
    }

    @Test
    public void validData() {
        fruitTransaction.setQuantity(9);
        operationStrategy.getOperation(FruitTransaction.Operation.PURCHASE)
                .operate(fruitTransaction);
        Integer actual = Storage.FRUITS_MAP.get("cherry");
        assertEquals(1, Integer.parseInt(String.valueOf(actual)));
    }
}
