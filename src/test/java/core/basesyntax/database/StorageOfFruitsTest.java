package core.basesyntax.database;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StorageOfFruitsTest {
    private static final String KEY = "banana";
    private static final Integer VALUE = 100;

    @Test
    void adding_In_Database_ok() {
        StorageOfFruits.fruitStorage.put(KEY,VALUE);
        assertEquals(StorageOfFruits.fruitStorage.get(KEY), VALUE);
    }
}

