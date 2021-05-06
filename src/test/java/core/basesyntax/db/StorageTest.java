package core.basesyntax.db;

import static org.junit.Assert.assertEquals;

import core.basesyntax.shop.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageTest {
    private static Map<Fruit, Integer> mapWithFruits;
    private static Fruit banana;

    @BeforeClass
    public static void beforeClass() {
        mapWithFruits = new HashMap<>();
        banana = new Fruit("banana");
        mapWithFruits.put(banana, 100);
    }

    @Test
    public void getFruits_Ok() {
        Storage.fruits.put(banana, 100);
        assertEquals(mapWithFruits, Storage.getFruits());
    }
}
