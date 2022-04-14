package core.basesyntax.handlers.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.handlers.FruitOperationHandler;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.dtos.FruitDtoTransaction;
import org.junit.Test;

public class AddOperationTest {
    private FruitOperationHandler fruitOperationHandler = new AddOperation();

    @Test
    public void addOperationWithCorrectData_Ok() {
        FruitDtoTransaction fruitDtoTransaction = new FruitDtoTransaction(
                OperationType.BALANCE, "apple", 50);
        fruitOperationHandler.apply(fruitDtoTransaction);
        int actual = Storage.getFruits().get(new Fruit("apple"));
        int expected = 50;
        assertEquals(expected, actual);
    }
}
