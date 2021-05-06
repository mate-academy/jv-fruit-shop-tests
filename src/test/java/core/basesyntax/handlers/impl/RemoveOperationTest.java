package core.basesyntax.handlers.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.handlers.FruitOperationHandler;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.dtos.FruitDtoTransaction;
import org.junit.After;
import org.junit.Test;

public class RemoveOperationTest {
    private FruitOperationHandler fruitOperationHandler = new RemoveOperation();

    @After
    public void afterEachTest() {
        Storage.getFruits().clear();
    }

    @Test
    public void addOperationWithCorrectData_Ok() {
        Storage.getFruits().put(new Fruit("apple"), 90);
        FruitDtoTransaction fruitDtoTransaction = new FruitDtoTransaction(
                OperationType.BALANCE, "apple", 50);
        fruitOperationHandler.apply(fruitDtoTransaction);
        int actual = Storage.getFruits().get(new Fruit("apple"));
        int expected = 40;
        assertEquals(expected, actual);
    }
}
