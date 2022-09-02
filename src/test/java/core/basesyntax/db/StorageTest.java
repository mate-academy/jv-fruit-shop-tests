package core.basesyntax.db;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class StorageTest {

    @Before
    public void setUp() {
        Storage.clear();
        Storage.add(new Fruit("apple"), 20);
        Storage.add(new Fruit("banana"), 30);
        Storage.add(new Fruit("orange"), 33);
    }

    @Test
    public void storage_addFruits_Ok() {
        Map<Fruit,Integer> expected = new HashMap<>();
        expected.put(new Fruit("apple"), 20);
        expected.put(new Fruit("banana"), 30);
        expected.put(new Fruit("orange"), 33);
        Map<Fruit, Integer> actual = Storage.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void storage_addMoreFruits_Ok() {
        Map<Fruit,Integer> expected = new HashMap<>();
        expected.put(new Fruit("apple"), 20);
        expected.put(new Fruit("banana"), 30);
        expected.put(new Fruit("orange"), 33);
        Storage.add(new Fruit("Pear"), 40);
        expected.put(new Fruit("Pear"), 40);
        Map<Fruit, Integer> actual = Storage.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void storage_getFruitQuantity_Ok() {
        Fruit testFruit = new Fruit("apple");
        Integer expected = 20;
        assertEquals(expected, Storage.get(testFruit));
    }
}
