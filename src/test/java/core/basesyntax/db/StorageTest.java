package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Map;
import org.junit.AfterClass;
import org.junit.Test;

public class StorageTest {
    @Test
    public void getCalculationMapReturnsEmptyMapInitially_Ok() {
        Map<String, Integer> calculationMap = Storage.getCalculationMap();
        assertTrue(calculationMap.isEmpty());
    }

    @Test
    public void getCalculationMapReturnsNonNullMap_OK() {
        Map<String, Integer> calculationMap = Storage.getCalculationMap();
        assertNotNull(calculationMap);
    }

    @Test
    public void getCalculationMapReturnsSameInstance_Ok() {
        Map<String, Integer> calculationMap1 = Storage.getCalculationMap();
        Map<String, Integer> calculationMap2 = Storage.getCalculationMap();
        assertSame(calculationMap1, calculationMap2);
    }

    @Test
    public void addToCalculationMap_Ok() {
        String key = "foo";
        int value = 42;
        Storage.getCalculationMap().put(key, value);
        assertEquals(Integer.valueOf(value),
                Storage.getCalculationMap().get(key));
    }

    @AfterClass
    public static void afterClass() {
        Storage.getCalculationMap().clear();
    }
}
