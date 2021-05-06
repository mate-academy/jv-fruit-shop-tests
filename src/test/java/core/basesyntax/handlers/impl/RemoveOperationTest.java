package core.basesyntax.handlers.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InsufficientQuantityException;
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
    public void removeOperationWithCorrectData_Ok() {
        Storage.getFruits().put(new Fruit("apple"), 90);
        FruitDtoTransaction fruitDtoTransaction = new FruitDtoTransaction(
                OperationType.BALANCE, "apple", 50);
        fruitOperationHandler.apply(fruitDtoTransaction);
        int actual = Storage.getFruits().get(new Fruit("apple"));
        int expected = 40;
        assertEquals(expected, actual);
    }

    @Test(expected = InsufficientQuantityException.class)
    public void removeOperationWithNotCorrectData_NotOK() {
        Storage.getFruits().put(new Fruit("apple"), 80);
        FruitDtoTransaction fruitDtoTransaction = new FruitDtoTransaction(
                OperationType.PURCHASE, "apple", 100);
        fruitOperationHandler.apply(fruitDtoTransaction);
    }
}
