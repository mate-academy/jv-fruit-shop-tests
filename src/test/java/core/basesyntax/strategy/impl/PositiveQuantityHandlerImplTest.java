package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation;
import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.QuantityHandler;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PositiveQuantityHandlerImplTest {
    private static final QuantityHandler quantityHandler = new PositiveQuantityHandlerImpl();

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void getQuantity_nullFruitTransaction_NotOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("FruitTransaction cannot be null");
        quantityHandler.getQuantity(null);
    }

    @Test
    public void getQuantity_nullFruit_NotOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Fruit cannot be null");
        quantityHandler.getQuantity(new FruitTransaction(Operation.BALANCE,null));
    }

    @Test
    public void getQuantity_nullFruitName_NotOk() {
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("Fruit name cannot be null");
        quantityHandler.getQuantity(new FruitTransaction(Operation.BALANCE,new Fruit(null,20)));
    }

    @Test
    public void getQuantity_validFruitTransaction_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                Operation.BALANCE, new Fruit("banana", 20));
        Integer expectedQuantity = 20;
        Integer actualQuantity = quantityHandler.getQuantity(fruitTransaction);
        assertEquals(expectedQuantity, actualQuantity);
    }
}
