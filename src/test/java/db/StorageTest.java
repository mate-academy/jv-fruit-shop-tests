package db;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageTest {
    private static final String FRUIT_FOR_TEST = "banana";
    private static final Integer AMOUNT_FOR_TEST = 5;

    @Before
    public void setUp() {
        Storage.fruits.put(FRUIT_FOR_TEST, AMOUNT_FOR_TEST);
    }

    @Test
    public void put_addToStorageFruit_Ok() {
        Integer actual = Storage.fruits.get(FRUIT_FOR_TEST);
        assertEquals(AMOUNT_FOR_TEST, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}

