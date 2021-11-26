package core.basesyntax.service.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.handler.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new ReturnOperationHandler(fruitDao);
    }

    @Before
    public void setUp() {
        Storage.storage.add(new Fruit("apple", 0));
        Storage.storage.add(new Fruit("pineapple", 155));
        Storage.storage.add(new Fruit("banana", 10));
        Storage.storage.add(new Fruit("ginger", 30));
    }

    @Test(expected = RuntimeException.class)
    public void apply_ReturnUnknownFruit_NotOk() {
        operationHandler.apply(200, "coconut");
    }

    @Test
    public void apply_ReturnValidNumber_Ok() {
        Fruit expected = new Fruit("apple", 150);
        Fruit actual = operationHandler.apply(150, "apple");
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
