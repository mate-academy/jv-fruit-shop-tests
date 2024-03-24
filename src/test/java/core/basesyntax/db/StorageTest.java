package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StorageTest {
    private final Storage storage = new Storage();
    private final Map<String, Integer> changedData = new HashMap<>();

    @Test
    void getData_ChangedData_Ok() {
        Map<String, Integer> actual = storage.getData();

        assertEquals(actual.getClass(), changedData.getClass());
    }
}
