package core.basesyntax.db.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageImplTest {
    private Fruit apple;
    private Fruit kiwi;
    private Map<Fruit, Integer> testMap;
    private Storage testStorage;

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        kiwi = new Fruit("kiwi");
        testStorage = new StorageImpl();
        testStorage.getStorage().put(apple, 33);
        testStorage.getStorage().put(kiwi, 66);
        testMap = Map.of(apple, 33, kiwi, 66);
    }

    @Test
    public void getStorageMethodTest_Ok() {
        assertEquals(testMap, testStorage.getStorage());
    }

    @Test
    public void getStorageAsListMethodTest_Ok() {
        List<String> expectedList = List.of("apple,33", "kiwi,66");
        List<String> storageAsList = testStorage.getStorageAsList();
        assertEquals(expectedList, storageAsList);
    }

    @After
    public void deleteAllDataFromStorage() {
        testStorage.getStorage().clear();
    }
}
