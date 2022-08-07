package core.basesyntax.db;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageTest {
    private static Storage fruitStorage;
    private static Map<String, Integer> excepted;

    @BeforeClass
    public static void beforeClass() {
        fruitStorage = new Storage();
        excepted = new HashMap<>();
    }

    @Before
    public void setUp() {
        fruitStorage.getFruitStorage().put("banana", 100);
        fruitStorage.getFruitStorage().put("apple", 100);
        fruitStorage.getFruitStorage().put("banana", 34);

        excepted.put("banana", 100);
        excepted.put("apple", 100);
        excepted.put("banana", 34);
    }

    @Test
    public void getFruitStorageAll_Ok() {
        Map<String, Integer> actual = StorageTest.fruitStorage.getFruitStorage();
        assertEquals("Test failed! HashMap expects to contain "
                + actual + " but was: ", excepted, actual);
    }

    @Test
    public void getSizeStorageMap_Ok() {
        assertEquals("Test failed! The size isn't correct. Expected 2 but was "
                + fruitStorage.getFruitStorage().size(), 2, fruitStorage.getFruitStorage().size());

        fruitStorage.getFruitStorage().put("chery", 23);
        assertEquals("Test failed! The size isn't correct. Expected 3 but was "
                + fruitStorage.getFruitStorage().size(), 3, fruitStorage.getFruitStorage().size());

        fruitStorage.getFruitStorage().remove("chery");
        assertEquals("Test failed! The size isn't correct. Expected 2 but was "
                + fruitStorage.getFruitStorage().size(), 2, fruitStorage.getFruitStorage().size());
    }

    @Test
    public void getValueStorage_Ok() {
        assertEquals("Test failed! Expected value 34, but wus: "
                + fruitStorage.getFruitStorage().get("banana"),
                Integer.valueOf(34), fruitStorage.getFruitStorage().get("banana"));
    }

    @AfterClass
    public static void afterClass() {
        fruitStorage.getFruitStorage().clear();
        excepted.clear();
    }
}
