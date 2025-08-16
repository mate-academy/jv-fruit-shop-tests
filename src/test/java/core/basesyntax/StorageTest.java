package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @Test
    void getQuantityExistingFruit() {
        Storage.getFruitStorage().put("Apple", 10);
        int result = Storage.getFruitStorage().get("Apple");
        assertEquals(10, result);
    }

    @Test
    void getQuantityNonExistingFruit() {
        int result = Storage.getFruitStorage().getOrDefault("Banana", 0);
        assertEquals(0, result);
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
