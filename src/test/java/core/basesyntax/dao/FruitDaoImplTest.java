package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dbfortest.FruitStorageForTests;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String BANANA_UPPERCASE = "BANANA";
    private static final String APPLE_UPPERCASE = "APPLE";
    private static final String NON_EXIST_FRUIT = "pineapple";
    private static FruitDao fruitDao;

    @BeforeClass
    public static void initFruitDao() {
        fruitDao = new FruitDaoImpl(FruitStorageForTests.fruitsStorage);
    }

    @Before
    public void fillStorage() {
        FruitStorageForTests.fruitsStorage.put("banana", 100);
        FruitStorageForTests.fruitsStorage.put("apple", 100);
    }

    @Test
    public void getData_ok() {
        Map<String, Integer> fromStorageForTest = fruitDao.getData();
        int bananaExpected = 100;
        int appleExpected = 100;
        int bananaActual = fromStorageForTest.get(BANANA);
        int appleActual = fromStorageForTest.get(APPLE);

        assertEquals(bananaExpected, bananaActual);
        assertEquals(appleExpected, appleActual);
    }

    @Test (expected = RuntimeException.class)
    public void getDataFromEmptyStorage_NotOk() {
        FruitStorageForTests.fruitsStorage.clear();
        Map<String, Integer> fromStorageForTest = fruitDao.getData();
    }

    @Test
    public void getFruitQuantity_fruitIsPresent_ok() {
        int bananaExpected = 100;
        int appleExpected = 100;
        int bananaActual = fruitDao.getFruitQuantity(BANANA);
        int appleActual = fruitDao.getFruitQuantity(APPLE);
        assertEquals("Bananas quantity should be " + bananaExpected
                        + ", but was: " + bananaActual,
                bananaExpected, bananaActual);
        assertEquals("Apples quantity should be " + appleExpected
                        + ", but was: " + appleActual,
                bananaExpected, bananaActual);
    }

    @Test
    public void getFruitQuantity_fruitNameUpperCase_ok() {
        int bananaExpected = 100;
        int appleExpected = 100;
        int bananaActual = fruitDao.getFruitQuantity(BANANA_UPPERCASE);
        int appleActual = fruitDao.getFruitQuantity(APPLE_UPPERCASE);
        assertEquals("Bananas quantity should be " + bananaExpected
                        + ", but was: " + bananaActual,
                bananaExpected, bananaActual);
        assertEquals("Apples quantity should be " + appleExpected
                        + ", but was: " + appleActual,
                bananaExpected, bananaActual);
    }

    @Test(expected = RuntimeException.class)
    public void getFruitQuantity_fruitNotExist_notOk() {
        int bananaActual = fruitDao.getFruitQuantity(NON_EXIST_FRUIT);
    }

    @Test
    public void update_ok() {
        int bananaExpected = 350;
        fruitDao.update(BANANA, 350);
        int bananaActual = FruitStorageForTests.fruitsStorage.get(BANANA);
        assertEquals("Bananas quantity should be " + bananaExpected
                        + ", but was: " + bananaActual,
                bananaExpected, bananaActual);
    }

    @Test
    public void update_addNewFruit_ok() {
        int pineappleExpected = 100;
        fruitDao.update(NON_EXIST_FRUIT, 100);
        int pineappleActual = FruitStorageForTests.fruitsStorage.get(NON_EXIST_FRUIT);
        assertEquals("Bananas quantity should be " + pineappleExpected
                        + ", but was: " + pineappleActual,
                pineappleExpected, pineappleActual);
    }

    @Test(expected = RuntimeException.class)
    public void update_addNullQuantity_notOk() {
        fruitDao.update(NON_EXIST_FRUIT, null);
    }

    @Test(expected = RuntimeException.class)
    public void update_addNullFruit_notOk() {
        fruitDao.update(null, 100);
    }

    @After
    public void clearStorage() {
        FruitStorageForTests.fruitsStorage.clear();
    }
}
