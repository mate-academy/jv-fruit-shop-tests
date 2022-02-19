package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import java.util.ArrayList;
import java.util.List;

public class FruitDaoImplTest {
    private static final int TEST_FRUIT_AMOUNT_ONE = 10;
    private static final int TEST_FRUIT_AMOUNT_TWO = 20;
    private static final String TEST_FRUIT_TYPE_ONE = "avocado";
    private static final String TEST_FRUIT_TYPE_TWO = "papaya";
    private static final String EMPTY_FRUIT_TYPE = "";
    private static final int ZERO_AMOUNT = 0;
    private static FruitDao fruitDao;
    private static List<Fruit> testListWithFruits;
    private Fruit testFruit;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        testListWithFruits = new ArrayList<>();
        Fruit testFruitOne = new Fruit();
        testFruitOne.setFruitType(TEST_FRUIT_TYPE_ONE);
        testFruitOne.setAmount(TEST_FRUIT_AMOUNT_ONE);
        Fruit testFruitTwo = new Fruit();
        testFruitTwo.setFruitType(TEST_FRUIT_TYPE_TWO);
        testFruitTwo.setAmount(TEST_FRUIT_AMOUNT_TWO);
        testListWithFruits.add(testFruitOne);
        testListWithFruits.add(testFruitTwo);
    }

    @Before
    public void setUp() {
        testFruit = new Fruit();
        testFruit.setFruitType(TEST_FRUIT_TYPE_ONE);
        testFruit.setAmount(TEST_FRUIT_AMOUNT_ONE);
        Storage.fruits.clear();

    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test(expected = RuntimeException.class)
    public void add_fruitNull_notOk() {
        fruitDao.add(null);
    }

    @Test(expected = RuntimeException.class)
    public void get_fruitTypeEmpty_notOk() {
        testFruit.setFruitType(EMPTY_FRUIT_TYPE);
        fruitDao.get(testFruit.getFruitType());
    }

    @Test(expected = RuntimeException.class)
    public void update_fruitNull_notOk() {
        fruitDao.update(null);
    }

    @Test(expected = RuntimeException.class)
    public void update_fruitTypeIsEmpty_notOk() {
        testFruit.setFruitType(EMPTY_FRUIT_TYPE);
        fruitDao.update(testFruit);
    }

    @Test(expected = RuntimeException.class)
    public void update_fruitAmountIsZero_notOk() {
        testFruit.setAmount(ZERO_AMOUNT);
        fruitDao.update(testFruit);
    }

    @Test
    public void add_newFruit_ok() {
        fruitDao.add(testFruit);
        Fruit actualFruit = fruitDao.get(testFruit.getFruitType());
        assertEquals(testFruit, actualFruit);
    }

    @Test
    public void update_fruitFromStorage_ok() {
        Storage.fruits.add(testFruit);
        testFruit.setAmount(TEST_FRUIT_AMOUNT_TWO);
        fruitDao.update(testFruit);
        Fruit actualFruit = fruitDao.get(testFruit.getFruitType());
        assertEquals(TEST_FRUIT_AMOUNT_TWO, actualFruit.getAmount());
    }

    @Test
    public void getAll_fruitsFromStorage_ok() {
        Storage.fruits.addAll(testListWithFruits);
        List<Fruit> actualListWithFruits = fruitDao.getAll();
        assertEquals(testListWithFruits, actualListWithFruits);
    }
}