package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    @Test
    public void return8_Banana_Ok() {
        ReturnOperationHandler returnOperationHandler = new ReturnOperationHandler();
        FruitTransaction fruitTransaction = new FruitTransaction("r", new Fruit("banana"), 8);
        Storage.storage.put(new Fruit("banana"), 10);
        returnOperationHandler.apply(fruitTransaction);
        Integer expected = 18;
        Integer actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }
}
