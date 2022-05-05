package core.basesyntax.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import java.util.Optional;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static Fruit testFruit;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        testFruit = new Fruit("grapes", 1020);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void create_Ok() {
        Fruit actual = fruitDao.create("grapes", 1020);
        assertEquals(testFruit, actual);
    }

    @Test
    public void save_Ok() {
        fruitDao.save(testFruit);
        Fruit actual = Storage.fruits.entrySet().stream()
                .map(f -> new Fruit(f.getKey(), f.getValue()))
                .findFirst().get();
        assertEquals(1, Storage.fruits.size());
        assertEquals(testFruit, actual);
    }

    @Test
    public void get_Ok() {
        Storage.fruits.put("grapes", 1020);
        Optional<Fruit> actual = fruitDao.get("grapes");
        assertEquals(1020, actual.get().getQuantity());
        assertEquals(testFruit, actual.get());
    }

    @Test
    public void getAll_Ok() {
        Storage.fruits.put("grapes", 100);
        Storage.fruits.put("banana", 120);
        Storage.fruits.put("apple", 37);
        List<Fruit> fruits = fruitDao.getAll();
        assertEquals(Storage.fruits.size(), fruits.size());
    }

    @Test
    public void update_Ok() {
        Storage.fruits.put("grapes", 1535);
        fruitDao.update(testFruit);
        assertEquals(2555, fruitDao.get("grapes").get().getQuantity());
    }

    @Test
    public void delete_Ok() {
        Storage.fruits.put("grapes", 100);
        fruitDao.delete(testFruit);
        assertEquals(0, Storage.fruits.size());
    }

    @Test
    public void create_notOk() {
        try {
            fruitDao.create("banana", -1000);
        } catch (RuntimeException e) {
            return;
        }
        fail("Quantity cannot be less than 0");
    }
}
