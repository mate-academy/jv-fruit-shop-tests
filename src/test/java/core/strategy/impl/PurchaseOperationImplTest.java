package core.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.dao.FruitDao;
import core.dao.FruitDaoImpl;
import core.db.Storage;
import core.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationImplTest {
    private static FruitDao fruitDao;
    private static PurchaseOperationImpl purchaseOperationImpl;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        purchaseOperationImpl = new PurchaseOperationImpl(fruitDao);
    }

    @Before
    public void setUp() {
        fruitDao.add(new Fruit("banana", 100));
    }

    @Test
    public void apply_correctWork_ok() {
        int expected = 20;
        purchaseOperationImpl.apply("banana", 80);
        int actual = fruitDao.get("banana").getQuantity();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseMoreThanBalance_notOk() {
        purchaseOperationImpl.apply("banana", 130);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

}
