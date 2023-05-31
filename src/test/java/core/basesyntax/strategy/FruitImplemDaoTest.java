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
    public void addFruit_ok() {
        fruitDao.addFruits("banana",17);
        fruitDao.addFruits("grapes",10);
        fruitDao.addFruits("strawberry",13);
        long actual = fruitDao.getAmountOfFruit("banana");
        assertEquals(17, actual);
    }

    @Test
    public void getFruitAmount_absentFruit_notOk() {
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

    @Test(expected = RuntimeException.class)
    public void getFruitAmountNullAmount_notOk() {
        fruitDao.addFruits("Banana",0);
    }

    @Test (expected = RuntimeException.class)
    public void getFruitAmount_emptyStorage_notOk() {
        fruitDao.getAmountOfFruit("plum");
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
