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

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        operationHandler = new PurchaseOperationHandler(fruitDao);
    }

    @Before
    public void setUp() {
        Storage.storage.add(new Fruit("apple", 200));
        Storage.storage.add(new Fruit("pineapple", 155));
        Storage.storage.add(new Fruit("banana", 10));
        Storage.storage.add(new Fruit("ginger", 30));
    }

    @Test(expected = RuntimeException.class)
    public void apply_NoThisFruit_NotOk() {
        operationHandler.apply(50, "lemon");
    }

    @Test(expected = RuntimeException.class)
    public void apply_NotEnoughFruits_NotOk() {
        operationHandler.apply(50, "banana");
    }

    @Test
    public void apply_PurchaseValidFruit_Ok() {
        Fruit expected = new Fruit("apple", 160);
        Fruit actual = operationHandler.apply(40, "apple");
        assertEquals(expected, actual);
    }

    @Test
    public void apply_PurchaseAllFruit_Ok() {
        Fruit expected = new Fruit("pineapple", 0);
        Fruit actual = operationHandler.apply(155, "pineapple");
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
