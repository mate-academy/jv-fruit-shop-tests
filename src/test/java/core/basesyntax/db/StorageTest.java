package core.basesyntax.db;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class StorageTest {
    @Test
    public void addFruitsToStorage_Ok() {
        String fruitName = "coconut";
        int fruitQuantity = 20;

        Map<String, Integer> reportMap = new HashMap<>();
        reportMap.put(fruitName, fruitQuantity);
        Storage.fruitsMap.put(fruitName, fruitQuantity);
        assertEquals(reportMap, Storage.fruitsMap);
    }

    @After
    public void afterEachTest() {
        Storage.fruitsMap.clear();
    }
}
