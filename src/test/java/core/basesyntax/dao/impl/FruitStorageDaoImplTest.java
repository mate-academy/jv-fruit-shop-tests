package core.basesyntax.dao.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.db.FruitStorage;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitStorageDaoImplTest {
    private FruitStorageDao dao;

    @Before
    public void setUp() {
        dao = new FruitStorageDaoImpl();
    }

    @Test
    public void updateWithEmptyStorage_ok() {
        dao.update("apple", 25);
        Assert.assertEquals("Expected value 25", 25, (int) FruitStorage.storage.get("apple"));
    }

    @Test
    public void updateWithNegativeValue_ok() {
        dao.update("apple", -25);
        Assert.assertEquals("Expected value -25", -25, (int) FruitStorage.storage.get("apple"));
    }

    @Test
    public void updateWithNotEmptyStorage_ok() {
        FruitStorage.storage.put("banana", 5);
        dao.update("banana", 10);
        Assert.assertEquals("Expected value 15", 15, (int) FruitStorage.storage.get("banana"));
    }

    @Test
    public void getEntriesWithTwoFruits_ok() {
        FruitStorage.storage.put("apple", 5);
        FruitStorage.storage.put("banana", 10);
        Set<Map.Entry<String, Integer>> entries = FruitStorage.storage.entrySet();
        Assert.assertEquals("Expected length of Set is 2", 2, entries.size());
        Optional<Map.Entry<String, Integer>> apple = entries.stream()
                .filter(e -> e.getKey().equals("apple"))
                .findFirst();
        Assert.assertTrue("Expected that entry of 'apple' is present", apple.isPresent());
        Assert.assertEquals("Expected quantity for 'apple' is 5", 5, (int) apple.get().getValue());
        Optional<Map.Entry<String, Integer>> banana = entries.stream()
                .filter(e -> e.getKey().equals("banana"))
                .findFirst();
        Assert.assertTrue("Expected that entry of 'banana' is present", banana.isPresent());
        Assert.assertEquals("Expected quantity for 'banana' is 10", 10,
                (int) banana.get().getValue());
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
