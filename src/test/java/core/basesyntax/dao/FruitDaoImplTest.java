package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.FruitDao;
import core.basesyntax.FruitDaoImpl;
import core.basesyntax.db.DataBase;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.ElementDoesNotExist;
import java.util.Map;
import java.util.TreeMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final String LEMON = "Lemon";
    private static final String KIVI = "kivi";
    private FruitDao fruitDao;
    private Fruit fruitOne;
    private Fruit fruitTwo;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        fruitOne = new Fruit();
        fruitOne.setBalance(50);
        fruitTwo = new Fruit();
        fruitTwo.setPurchase(100);
    }

    @Test
    public void add_ok() {
        fruitDao.add(LEMON, fruitOne);
        int expected = 1;
        assertEquals(expected, DataBase.fruitsInShop.size());
        assertEquals(fruitOne, DataBase.fruitsInShop.get(LEMON));
    }

    @Test(expected = ElementDoesNotExist.class)
    public void add_keyIsNull_notOk() {
        fruitDao.add(null, fruitOne);
    }

    @Test(expected = ElementDoesNotExist.class)
    public void add_valueIsNull_notOk() {
        fruitDao.add("lemon", null);
    }

    @Test(expected = ElementDoesNotExist.class)
    public void get_valueIsnull_notOk() {
        fruitDao.get(null);
    }

    @Test
    public void get_ok() {
        DataBase.fruitsInShop.put(LEMON, fruitOne);
        DataBase.fruitsInShop.put(KIVI, fruitTwo);
        assertEquals(fruitOne, fruitDao.get(LEMON));
        assertEquals(fruitTwo, fruitDao.get(KIVI));
    }

    @Test
    public void getAll_ok() {
        Map<String, Fruit> expectedMap = new TreeMap<>();
        expectedMap.put(LEMON, fruitOne);
        expectedMap.put(KIVI, fruitTwo);
        DataBase.fruitsInShop.put(LEMON, fruitOne);
        DataBase.fruitsInShop.put(KIVI, fruitTwo);
        assertEquals(expectedMap, fruitDao.getAll());
    }

    @After
    public void tearDown() {
        DataBase.fruitsInShop.clear();
    }
}
