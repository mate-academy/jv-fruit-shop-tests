package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Test;

public class StorageTest {
    private static final String FRUIT = "apple";
    private static final Integer AMOUNT_TO_PUT = 23;

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void put_value_with_null_Ok() {
        Storage.put(FRUIT, null);
        assertNull(Storage.get(FRUIT));
    }

    @Test
    public void get_and_put_with_existing_key_Ok() {
        Storage.put(FRUIT, null);
        Storage.put(FRUIT, AMOUNT_TO_PUT);
        assertEquals(Storage.get(FRUIT), AMOUNT_TO_PUT);
    }

    @Test
    public void get_with_null_key_and_null_value_Ok() {
        Storage.get(null);
        assertNull(null);
    }
}
