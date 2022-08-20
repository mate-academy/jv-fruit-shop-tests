package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.FruitShopStorage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class StorageDaoImplTest {
    private static final StorageDao daoTest = new StorageDaoImpl();

    @After
    public void clearStorage() {
        FruitShopStorage.storageFruits.clear();
    }

    @Test
    public void add_fruit_Ok() {
        Fruit apple = new Fruit("apple", 20);
        daoTest.addFruit(apple);
        boolean actual = FruitShopStorage.storageFruits.size() == 1;
        assertTrue(actual);
    }

    @Test
    public void get_fruit_Ok() {
        Fruit apple = new Fruit("apple", 20);
        daoTest.addFruit(apple);
        String expected = "apple";
        String actual = daoTest.getFruit("apple").getName();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_wrongFruits_notOk() {
        String wrongFruit = "pineapple";
        daoTest.getFruit(wrongFruit);
    }

    @Test
    public void get_allFruit_Ok() {
        int expected = 2;
        Fruit orange = new Fruit("orange", 40);
        Fruit peach = new Fruit("peach", 18);
        daoTest.addFruit(orange);
        daoTest.addFruit(peach);
        List<Fruit> testList = daoTest.getAll();
        boolean actual = testList.size() == expected;
        assertTrue(actual);
    }
}
