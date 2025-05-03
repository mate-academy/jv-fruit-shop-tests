package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Test;

public class PositiveOperationHandlerImplTest {
    private static final OperationHandler operationHandler = new PositiveOperationHandlerImpl();

    @Test
    public void getQuantity_validFruitTransaction_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                Operation.BALANCE, new Fruit("banana", 20));
        Integer expectedQuantity = 20;
        Integer actualQuantity = operationHandler.getQuantity(fruitTransaction);
        assertEquals(expectedQuantity, actualQuantity);
    }
}
