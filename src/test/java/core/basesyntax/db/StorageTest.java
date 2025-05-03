package core.basesyntax.db;

import static core.basesyntax.constants.Constants.APPLE;
import static core.basesyntax.constants.Constants.BANANA;
import static core.basesyntax.constants.Constants.CHERRY;
import static core.basesyntax.constants.Constants.NORMAL_QUANTITY;
import static core.basesyntax.constants.Constants.ZERO_ELEMENTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class StorageTest {

    public void setUp() {
        Storage.updateDb(APPLE, ZERO_ELEMENTS);
        Storage.updateDb(BANANA, ZERO_ELEMENTS);
    }

    @Test
    public void updateDb_getQuantity_correct_ok() {
        Storage.updateDb(APPLE, NORMAL_QUANTITY);
        Storage.updateDb(BANANA, NORMAL_QUANTITY);
        assertEquals(NORMAL_QUANTITY, Storage.getQuantity(APPLE));
        assertEquals(NORMAL_QUANTITY, Storage.getQuantity(BANANA));
    }

    @Test
    public void updateDb_isCopyOfMapReturned_ok() {
        Storage.updateDb(APPLE, NORMAL_QUANTITY);
        Map<String, Integer> testMap = Storage.readDb();
        testMap.put(CHERRY, NORMAL_QUANTITY);
        assertNull(Storage.getQuantity(CHERRY));
    }

    @Test
    void readDb_correctReadDb_ok() {
        Storage.updateDb(APPLE, NORMAL_QUANTITY);
        Storage.updateDb(BANANA, NORMAL_QUANTITY);
        Map<String, Integer> testMap = Storage.readDb();
        assertEquals(NORMAL_QUANTITY, testMap.get(APPLE));
        assertEquals(NORMAL_QUANTITY, testMap.get(BANANA));
    }
}
