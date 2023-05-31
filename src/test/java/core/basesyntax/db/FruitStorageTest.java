package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class FruitStorageTest {
    @Before
    public void setUp() {
        FruitStorage.fruits.put("apple", 50);
    }

    @Test
    public void storage_addValidData_Ok() {
        Integer expected = 50;
        Integer actual = FruitStorage.fruits.get("apple");
        assertEquals(expected, actual);
    }

    @Test
    public void storage_addInvalidData_Ok() {
        Integer actual = FruitStorage.fruits.get("Bad data");
        assertNull(actual);
    }

    @Test
    public void storage_addNullData_Ok() {
        Integer actual = FruitStorage.fruits.get(null);
        assertNull(actual);
    }
}
