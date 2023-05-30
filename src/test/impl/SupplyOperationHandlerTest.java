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

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new SupplyOperationHandler(fruitDao);
    }

    @Before
    public void setUp() {
        Storage.storage.add(new Fruit("apple", 120));
        Storage.storage.add(new Fruit("pineapple", 155));
        Storage.storage.add(new Fruit("banana", 35));
        Storage.storage.add(new Fruit("ginger", 30));
    }

    @Test
    public void apply_SupplyNewFruit_Ok() {
        Fruit expected = new Fruit("coconut", 50);
        Fruit actual = operationHandler.apply(50, "coconut");
        assertEquals(expected, actual);
    }

    @Test
    public void apply_SupplyFruitAtStorage_Ok() {
        Fruit expected = new Fruit("ginger", 60);
        Fruit actual = operationHandler.apply(30, "ginger");
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_SupplyZeroFruits_NotOk() {
        operationHandler.apply(0, "apple");
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
