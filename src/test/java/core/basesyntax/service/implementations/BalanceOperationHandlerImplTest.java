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

public class BalanceOperationHandlerImplTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeAll() {
        FruitsDao fruitsDao = new FruitsDaoImpl();
        handler = new BalanceOperationHandlerImpl(fruitsDao);
    }

    @After
    public void afterEach() {
        Storage.storage.clear();
    }

    @Test
    public void doOperation_RightData_Ok() {
        Fruit apple = new Fruit("apple");
        handler.doOperation(apple,25);
        Map<Fruit, Integer> expected = new HashMap<>() {
            {
                put(apple, 25);
            }
        };
        Map<Fruit, Integer> actual = Storage.storage;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void doOperation_ChangeValue_Ok() {
        Fruit apple = new Fruit("apple");
        handler.doOperation(apple,25);
        handler.doOperation(apple,14);
        Map<Fruit, Integer> expected = new HashMap<>() {
            {
                put(apple, 25);
                put(apple, 14);
            }
        };
        Map<Fruit, Integer> actual = Storage.storage;
        Assert.assertEquals(expected, actual);
    }
}
