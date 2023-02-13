package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

public class FruitStorageTest {

    @Before
    public void setUp() {
        FruitStorage.fruits.put("banana", 139);
    }

    @Test
    public void storage_GetCorrectData_Ok() {
        Integer expected = 139;
        Integer actual = FruitStorage.fruits.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void storage_GetIncorrectData_Ok() {
        Integer expected = 139;
        Integer actual = FruitStorage.fruits.get("notfruit");
        assertNull(actual);
    }

    @Test
    public void storage_GetNullFromData_Ok() {
        Integer expected = 139;
        Integer actual = FruitStorage.fruits.get(null);
        assertNull(actual);
    }
}
