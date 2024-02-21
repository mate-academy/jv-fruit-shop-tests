package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Map;
import org.junit.jupiter.api.Test;

class StorageTest {

    @Test
    void getFruitStorage_sameInstanceAlwaysReturned_Ok() {
        Map<String, Integer> storage = Storage.getFruitStorage();

        assertNotNull(storage);
        assertSame(storage, Storage.getFruitStorage());
    }
}
