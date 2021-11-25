package core.basesyntax.service.handler.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.handler.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new BalanceOperationHandler(fruitDao);
    }

    @Test
    public void apply_AddOneFruit_Ok() {
        Fruit fruit = operationHandler.apply(25, "apple");
        assertTrue(Storage.storage.contains(fruit));
    }

    @Test(expected = RuntimeException.class)
    public void apply_AddTwoEqualsFruits_NotOk() {
        operationHandler.apply(50, "banana");
        operationHandler.apply(50, "banana");
    }

    @Test
    public void apply_AddTwoDifferentFruits() {
        Fruit fruit1 = operationHandler.apply(150, "pineapple");
        Fruit fruit2 = operationHandler.apply(50, "apple");
        assertTrue(Storage.storage.contains(fruit1));
        assertTrue(Storage.storage.contains(fruit2));
    }

    @After
    public void afterClass() {
        Storage.storage.clear();
    }
}
