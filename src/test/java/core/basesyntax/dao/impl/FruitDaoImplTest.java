package core.basesyntax.dao.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.repository.FruitDB;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitDaoImplTest {
    private static FruitDao fruitDao;
    private static final String FRUIT_1 = "banana";
    private static final String FRUIT_2 = "apple";
    private static final int AMOUNT = 15;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
    }

    @Before
    public void setUp() {
        FruitDB.fruitsOnStock.put(FRUIT_1, AMOUNT);
    }

    @After
    public void tearDown() {
        FruitDB.fruitsOnStock.clear();
    }

    @Test
    public void updateStock_sellingNotAvailableProducts_notOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Client wants to buy more products than are available");
        fruitDao.updateStock(FRUIT_1, Integer.MIN_VALUE);
    }

    @Test
    public void updateStock_size1_Ok() {
        assertEquals(1, fruitDao.getStock().size());
    }

    @Test
    public void updateStock_sizeGrowing_Ok() {
        fruitDao.updateStock(FRUIT_2, AMOUNT);
        assertEquals(2, fruitDao.getStock().size());
    }

    @Test
    public void getStock_isEmptyByDefault_Ok() {
        FruitDB.fruitsOnStock.clear();
        assertEquals(0, fruitDao.getStock().size());
    }

    @Test
    public void getStock_regularCase_Ok() {
        Map<String, Integer> expected = Map.of(FRUIT_1, AMOUNT);
        assertEquals(expected.get(FRUIT_1), fruitDao.getStock().get(FRUIT_1));
    }
}

