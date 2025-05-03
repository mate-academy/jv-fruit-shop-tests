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

public class BalanceActivityHandlerTest {
    private static BalanceActivityHandler balanceActivityHandler;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        balanceActivityHandler = new BalanceActivityHandler(fruitDao);
    }

    @Before
    public void setUp() {
        fruitDao.add(new Fruit("banana", 23));
    }

    @Test
    public void doActivity_validData_ok() {
        int expected = 30;
        balanceActivityHandler.doActivity("banana", 30);
        int actual = fruitDao.get("banana").getQuantity();
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
