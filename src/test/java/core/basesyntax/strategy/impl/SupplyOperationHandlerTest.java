package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    @Test
    public void supply88_Banana_Ok() {
        SupplyOperationHandler supplyOperationHandler = new SupplyOperationHandler();
        Storage.storage.put(new Fruit("banana"), 88);
        FruitTransaction fruitTransaction = new FruitTransaction("s", new Fruit("banana"), 88);
        supplyOperationHandler.apply(fruitTransaction);
        Integer expected = 176;
        Integer actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }
}
