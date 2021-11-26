package core.basesyntax.startegy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseActivityHandlerTest {
    private static PurchaseActivityHandler purchaseActivityHandler;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        purchaseActivityHandler = new PurchaseActivityHandler(fruitDao);
    }

    @Before
    public void setUp() {
        fruitDao.add(new Fruit("banana", 23));
    }

    @Test
    public void doActivity_validData_ok() {
        int expected = 20;
        purchaseActivityHandler.doActivity("banana", 3);
        int actual = fruitDao.get("banana").getQuantity();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void doActivity_purchaseMoreThanBalance_notOk() {
        purchaseActivityHandler.doActivity("banana", 25);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

}
