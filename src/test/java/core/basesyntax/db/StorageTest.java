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
        Storage.reportMap.put(fruitName, fruitQuantity);
        assertEquals(reportMap, Storage.reportMap);
    }

    @After
    public void afterEachTest() {
        Storage.reportMap.clear();
    }
}
