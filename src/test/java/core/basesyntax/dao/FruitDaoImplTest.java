package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDao testFruitDao;
    private static final String TEST_FRUIT1 = "apple";
    private static final int TEST_AMOUNT1 = 5;
    private static final String TEST_FRUIT2 = "banana";
    private static final int TEST_AMOUNT2 = 10;

    @Before
    public void setUp() {
        testFruitDao = new FruitDaoImpl();
    }

    @Test
    public void putAndGet_Ok() {
        testFruitDao.put(TEST_FRUIT1, TEST_AMOUNT1);
        testFruitDao.put(TEST_FRUIT2, TEST_AMOUNT2);
        int actualAmount1 = testFruitDao.get(TEST_FRUIT1);
        int actualAmount2 = testFruitDao.get(TEST_FRUIT2);
        assertEquals(TEST_AMOUNT1, actualAmount1);
        assertEquals(TEST_AMOUNT2, actualAmount2);
    }

    @Test
    public void getFruits_Ok() {
        testFruitDao.put(TEST_FRUIT1, TEST_AMOUNT1);
        testFruitDao.put(TEST_FRUIT2, TEST_AMOUNT2);
        List<FruitDto> actualListFruitDto = testFruitDao.getFruits();
        assertEquals("Wrong list size!", 2, actualListFruitDto.size());
        for (FruitDto fruitDto : actualListFruitDto) {
            if (fruitDto.getFruit().equals(TEST_FRUIT1)) {
                assertEquals("Incorrect amount value!", TEST_AMOUNT1, fruitDto.getAmount());
                continue;
            }
            if (fruitDto.getFruit().equals(TEST_FRUIT2)) {
                assertEquals("Incorrect amount value!", TEST_AMOUNT2, fruitDto.getAmount());
                continue;
            }
            fail("Unknown fruit in storage - " + fruitDto.getFruit());
        }
    }

    @After
    public void tearDown() {
        testFruitDao.clear();
    }
}
