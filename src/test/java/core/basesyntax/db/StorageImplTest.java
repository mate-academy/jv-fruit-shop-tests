package core.basesyntax.db;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

public class StorageImplTest {
    private Storage storage;

    @Before
    public void setUp() {
        storage = new StorageImpl();
        storage.add(new Fruit("pineapple"), 50);
        storage.add(new Fruit("lemon"), 70);
        storage.add(new Fruit("orange"), 120);
    }

    @Test
    public void storageImpl_getAllFruits_OK() {
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("pineapple"), 50);
        expectedMap.put(new Fruit("lemon"), 70);
        expectedMap.put(new Fruit("orange"), 120);
        Set<Map.Entry<Fruit, Integer>> expected = expectedMap.entrySet();
        Set<Map.Entry<Fruit, Integer>> actual = storage.getAllFruits();
        assertEquals(expected, actual);
    }

    @Test
    public void storageImpl_addFruit_OK() {
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("pineapple"), 50);
        expectedMap.put(new Fruit("lemon"), 70);
        expectedMap.put(new Fruit("orange"), 120);
        expectedMap.put(new Fruit("banana"), 150);
        storage.add(new Fruit("banana"), 150);
        Set<Map.Entry<Fruit, Integer>> expected = expectedMap.entrySet();
        Set<Map.Entry<Fruit, Integer>> actual = storage.getAllFruits();
        assertEquals(expected, actual);
    }

    @Test
    public void storageImpl_removeFruit_OK() {
        Map<Fruit, Integer> expectedMap = new HashMap<>();
        expectedMap.put(new Fruit("pineapple"), 50);
        expectedMap.put(new Fruit("lemon"), 70);
        expectedMap.put(new Fruit("orange"), 120);
        expectedMap.put(new Fruit("orange"), 100);
        storage.remove(new Fruit("orange"), 20);
        Set<Map.Entry<Fruit, Integer>> expected = expectedMap.entrySet();
        Set<Map.Entry<Fruit, Integer>> actual = storage.getAllFruits();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void storageImpl_removeFromStorageMoreThenTotalAmount_NotOK() {
        storage.remove(new Fruit("lemon"), 150);
    }
}
