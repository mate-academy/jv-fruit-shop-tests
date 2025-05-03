package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.StorageImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    private static final String FRUIT = "banana";
    private static final int QUANTITY = 100;

    @AfterEach
    public void clear() {
        StorageImpl.getStorage().clear();
    }

    @Test
    public void storage_get_Ok() {
        StorageImpl.getStorage().put(FRUIT, QUANTITY);

        assertAll("Test failed! The Storage should be contain: " + FRUIT + " = " + QUANTITY,
                () -> assertTrue(StorageImpl.getStorage().containsKey(FRUIT)),
                () -> assertEquals(QUANTITY, StorageImpl.getStorage().get(FRUIT))
        );
    }
}
