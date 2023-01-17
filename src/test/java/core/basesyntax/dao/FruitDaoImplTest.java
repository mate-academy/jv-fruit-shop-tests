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
    private final String lemon = "Lemon";
    private final String kivi = "kivi";
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
        fruitDao.add(lemon, fruitOne);
        int expected = 1;
        assertEquals(expected, DataBase.fruitsInShop.size());
        assertEquals(fruitOne, DataBase.fruitsInShop.get(lemon));
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
        DataBase.fruitsInShop.put(lemon, fruitOne);
        DataBase.fruitsInShop.put(kivi, fruitTwo);
        assertEquals(fruitOne, fruitDao.get(lemon));
        assertEquals(fruitTwo, fruitDao.get(kivi));
    }

    @Test
    public void getAll_ok() {
        Map<String, Fruit> expectedMap = new TreeMap<>();
        expectedMap.put(lemon, fruitOne);
        expectedMap.put(kivi, fruitTwo);
        DataBase.fruitsInShop.put(lemon, fruitOne);
        DataBase.fruitsInShop.put(kivi, fruitTwo);
        assertEquals(expectedMap, fruitDao.getAll());
    }

    @After
    public void tearDown() {
        DataBase.fruitsInShop.clear();
    }
}
