package core.basesyntax.storage;

import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DataBaseImplTest {
    private Storage storage;

    @Before
    public void setUp() {
        storage = new StorageImpl();
    }

    @After
    public void tearDown() {
        storage.clearStorage();
    }

    @Test
    public void addFruits_Ok() {
        storage.add("apple", 100);
        Set<Map.Entry<String, Integer>> actualResult = storage.getEntrySet();
        Set<Map.Entry<String, Integer>> expectedResult = Map.of("apple", 100).entrySet();
        Assert.assertEquals(expectedResult, actualResult);
        storage.remove("apple", 100);
    }

    @Test
    public void deleteFruit_Ok() {
        storage.add("apple", 100);
        storage.remove("apple", 40);
        Set<Map.Entry<String, Integer>> actualResult = storage.getEntrySet();
        Set<Map.Entry<String, Integer>> expectedResult = Map.of("apple", 60).entrySet();
        Assert.assertEquals(expectedResult, actualResult);
    }

    @Test (expected = RuntimeException.class)
    public void deleteFruitWithWrongNameOfFruit_NotOk() {
        storage.add("apple", 100);
        storage.remove("orange", 5);
    }

    @Test (expected = RuntimeException.class)
    public void deleteFruitWithWrongBiggerNumber_NotOk() {
        storage.add("grape", 50);
        storage.remove("grape", 100);
    }
}
