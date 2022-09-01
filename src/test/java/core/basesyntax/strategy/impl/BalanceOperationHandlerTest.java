package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.Test;

public class BalanceOperationHandlerTest {

    @Test
    public void balanceBanana88_Ok() {
        BalanceOperationHandler balanceOperationHandler = new BalanceOperationHandler();
        FruitTransaction fruitTransaction = new FruitTransaction("b", new Fruit("banana"), 88);
        balanceOperationHandler.apply(fruitTransaction);
        Integer expected = 88;
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        Assert.assertEquals(expected, actual);
    }
}
