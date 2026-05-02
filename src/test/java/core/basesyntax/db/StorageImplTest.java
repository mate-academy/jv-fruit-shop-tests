package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.db.Storage;
import core.basesyntax.basesyntax.db.StorageImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StorageImplTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final int QUANTITY_0 = 0;
    private static final int QUANTITY_10 = 10;
    private static final int QUANTITY_15 = 15;
    private static final int QUANTITY_20 = 20;

    private static Storage storage;

    @BeforeAll
    static void setUp() {
        storage = new StorageImpl();
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void getQuantity_defaultZero_ok() {
        assertEquals(QUANTITY_0, storage.getQuantity(FRUIT_APPLE));
    }

    @Test
    void setAndGetQuantity_ok() {
        storage.setQuantity(FRUIT_APPLE, QUANTITY_15);
        assertEquals(QUANTITY_15, storage.getQuantity(FRUIT_APPLE));
    }

    @Test
    void setQuantity_overwriteQuantity_ok() {
        storage.setQuantity(FRUIT_BANANA, QUANTITY_10);
        storage.setQuantity(FRUIT_BANANA, QUANTITY_20);

        assertEquals(QUANTITY_20, storage.getQuantity(FRUIT_BANANA));
    }
}
