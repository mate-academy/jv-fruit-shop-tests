package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.DaoFruit;
import core.basesyntax.dao.FruitImplemDao;
import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitImplemDaoTest {
    private static DaoFruit fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitImplemDao();
    }

    @Test
    public void addFruit_Ok() {
        fruitDao.addFruits("banana",17);
        fruitDao.addFruits("grapes",10);
        fruitDao.addFruits("strawberry",13);
        long actual = fruitDao.getAmountOfFruit("banana");
        assertEquals(17, actual);
    }

    @Test
    public void getFruitAmount_absentFruit_NotOk() {
        fruitDao.addFruits("banana",17);
        fruitDao.addFruits("grapes",10);
        fruitDao.addFruits("strawberry",13);
        try {
            fruitDao.getAmountOfFruit("pink");
        } catch (Exception e) {
            assertEquals("There is no pink in storage",
                    e.getMessage());
            return;
        }
        fail("Expected validation exception was not thrown");
    }

    @Test
    public void getFruitAmount_NullAmount_notOk() {
        try {
            fruitDao.addFruits("Banana",0);
        } catch (RuntimeException e) {
            assertEquals("Ammount of added fruit cannot be 0",
                    e.getMessage());
            return;
        }
        fail("Expected validation exception was not thrown");

    }

    @Test (expected = RuntimeException.class)
    public void getFruitAmount_emptyStorage_NotOk() {
        fruitDao.getAmountOfFruit("plum");
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
