package dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Map;
import java.util.Set;
import org.junit.Test;
import storage.DataStorage;

public class DataDaoImplTest {
    private static final Integer EXPECTED_AMOUNT = 50;
    private static final String FRUIT = "banana";
    private static final String SUPER_FRUIT = "potato";

    @Test
    public void putValue_ok() {
        new DataDaoImpl().putValue(FRUIT, EXPECTED_AMOUNT);
        Integer actualAmount = DataStorage.FRUIT_MAP.get(FRUIT);
        assertEquals("Failed to put correct amount into storage for fruit: " + FRUIT,
                EXPECTED_AMOUNT, actualAmount);
    }

    @Test
    public void getValue_ok() {
        DataStorage.FRUIT_MAP.put(FRUIT, EXPECTED_AMOUNT);
        Integer actualAmount = new DataDaoImpl().getValue(FRUIT);
        assertEquals("Failed to return correct amount from storage for fruit: " + FRUIT,
                EXPECTED_AMOUNT, actualAmount);
    }

    @Test
    public void getValue_null_ok() {
        DataStorage.FRUIT_MAP.put(FRUIT, EXPECTED_AMOUNT);
        Integer actualAmount = new DataDaoImpl().getValue(SUPER_FRUIT);
        assertNull("Should return null for non-existing fruit in storage", actualAmount);
    }

    @Test
    public void getFruitMap_ok() {
        DataStorage.FRUIT_MAP.put(FRUIT, EXPECTED_AMOUNT);
        DataStorage.FRUIT_MAP.put(SUPER_FRUIT, EXPECTED_AMOUNT);
        Set<Map.Entry<String, Integer>> entries = new DataDaoImpl().getFruitMap().entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            assertEquals("Failed to return correct amount from storage for fruit: "
                            + entry.getKey(),
                    EXPECTED_AMOUNT, entry.getValue());
        }
    }
}
