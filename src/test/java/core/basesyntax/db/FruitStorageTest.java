package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class FruitStorageTest {
    @Before
    public void setUp() {
        FruitStorage.fruits.put("orange", 100);
    }

    @Test
    public void fruitStorage_addValidData_ok() {
        Integer expected = 100;
        Integer actual = FruitStorage.fruits.get("orange");
        assertEquals(expected, actual);
    }

    @Test
    public void fruitStorage_addNullData_notOk() {
        Integer actual = FruitStorage.fruits.get(null);
        assertNull(actual);
    }

    @Test
    public void fruitStorage_addInvalidData_notOk() {
        Integer actual = FruitStorage.fruits.get("cucumber");
        assertNull(actual);
    }

    @AfterClass
    public static void afterClass() {
        FruitStorage.fruits.clear();
    }
}
