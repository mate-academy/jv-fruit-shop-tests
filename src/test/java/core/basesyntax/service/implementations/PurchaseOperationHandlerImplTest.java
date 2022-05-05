package core.basesyntax.service.implementations;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.inerfaces.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerImplTest {
    private static OperationHandler handler;
    private static FruitsDao fruitsDao;

    @BeforeClass
    public static void beforeAll() {
        fruitsDao = new FruitsDaoImpl();
        handler = new PurchaseOperationHandlerImpl(fruitsDao);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }

    @Test
    public void doOperation_RightData_Ok() {
        Fruit apple = new Fruit("apple");
        Fruit pear = new Fruit("pear");
        Storage.storage.put(apple, 10);
        Storage.storage.put(pear, 10);
        Map<Fruit, Integer> expected = new HashMap<>() {
            {
                put(apple, 7);
                put(pear, 5);
            }
        };
        handler.doOperation(apple, 3);
        handler.doOperation(pear, 5);
        Map<Fruit, Integer> actual = Storage.storage;
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void doOperation_MapNull_NotOk() {
        Fruit apple = new Fruit("apple");
        handler.doOperation(apple, 424);
    }
}
