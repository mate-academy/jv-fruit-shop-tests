package core.basesyntax.dao;

import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.impl.FruitDaoImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoTest {
    private static final String DEFAULT_FRUIT_NAME = "apple";
    private static final String ADDITIONAL_FRUIT_NAME = "banana";
    private static final int DEFAULT_FRUIT_AMOUNT = 10;
    private static final int ADDITIONAL_FRUIT_AMOUNT = 20;
    private static final int EMPTY_SIZE = 0;
    private static final int SIZE_ONE_ELEMENT = 1;
    private static final int GOT_NEGATIVE_INDEX = -1;

    private static FruitDao fruitDao;

    @BeforeAll
    static void setUp() {
        fruitDao = new FruitDaoImpl();
    }

    @BeforeEach
    void setFruitDao() {
        fruitDao.addFirst(DEFAULT_FRUIT_NAME, DEFAULT_FRUIT_AMOUNT);
    }

    @Test
    void addFirst_correctInput_Ok() {
        int actual = fruitDao.get(DEFAULT_FRUIT_NAME);
        int expected = DEFAULT_FRUIT_AMOUNT;
        assertTrue(expected == actual);
    }

    @Test
    void add_correctInput_Ok() {
        fruitDao.add(DEFAULT_FRUIT_NAME, DEFAULT_FRUIT_AMOUNT);
        int actual = fruitDao.get(DEFAULT_FRUIT_NAME);
        int expected = ADDITIONAL_FRUIT_AMOUNT;
        assertTrue(expected == actual);
    }

    @Test
    void get_correctInputData_Ok() {
        int actual = fruitDao.get(DEFAULT_FRUIT_NAME);
        int expected = DEFAULT_FRUIT_AMOUNT;
        assertTrue(expected == actual);
    }

    @Test
    void get_fruitNotFound_Ok() {
        int actual = fruitDao.get(ADDITIONAL_FRUIT_NAME);
        int expected = GOT_NEGATIVE_INDEX;
        assertTrue(expected == actual);
    }

    @Test
    void getAll_correctInputData_Ok() {
        String expectedFruitName = DEFAULT_FRUIT_NAME;
        int expectedFruitAmount = DEFAULT_FRUIT_AMOUNT;
        assertTrue(fruitDao.get(expectedFruitName) == expectedFruitAmount);
    }

    @Test
    void remove_correctInputData_Ok() {
        fruitDao.reduce(DEFAULT_FRUIT_NAME, DEFAULT_FRUIT_AMOUNT);
        int expected = EMPTY_SIZE;
        assertTrue(expected == fruitDao.get(DEFAULT_FRUIT_NAME));
    }

    @Test
    void removeAll_correctInputData_Ok() {
        fruitDao.removeAll();
        int expected = EMPTY_SIZE;
        assertTrue(expected == fruitDao.size());
    }

    @Test
    void size_getActualSize_Ok() {
        int expectedMapSize = SIZE_ONE_ELEMENT;
        assertTrue(expectedMapSize == fruitDao.size());
    }

    @AfterEach
    void cleanFruitStorage() {
        fruitDao.removeAll();
    }
}
