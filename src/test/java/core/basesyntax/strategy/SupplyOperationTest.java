package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.db.StorageImpl;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import core.basesyntax.basesyntax.strategy.SupplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_ORANGE = "orange";
    private static final String FRUIT_GRAPES = "grapes";
    private static final String FRUIT_TANGERINE = "tangerine";
    private static final String FRUIT_MANGO = "mango";
    private static final int QUANTITY_0 = 0;
    private static final int QUANTITY_10 = 10;
    private static final int QUANTITY_15 = 15;
    private static final int QUANTITY_20 = 20;
    private static final int QUANTITY_25 = 25;
    private static final int QUANTITY_35 = 35;

    private OperationHandler operation;

    @BeforeEach
    void setUp() {
        operation = new SupplyOperation();
    }

    @Test
    void apply_newFruit_ok() {
        StorageImpl storage = new StorageImpl();

        operation.apply(FRUIT_ORANGE, QUANTITY_15, storage);
        operation.apply(FRUIT_APPLE, QUANTITY_20, storage);
        operation.apply(FRUIT_BANANA, QUANTITY_25, storage);

        assertEquals(QUANTITY_15, storage.getQuantity(FRUIT_ORANGE));
        assertEquals(QUANTITY_20, storage.getQuantity(FRUIT_APPLE));
        assertEquals(QUANTITY_25, storage.getQuantity(FRUIT_BANANA));
    }

    @Test
    void apply_increaseExistingFruit_ok() {
        StorageImpl storage = new StorageImpl();
        storage.setQuantity(FRUIT_TANGERINE, QUANTITY_15);
        storage.setQuantity(FRUIT_GRAPES, QUANTITY_10);

        operation.apply(FRUIT_TANGERINE, QUANTITY_20, storage);
        operation.apply(FRUIT_GRAPES, QUANTITY_15, storage);

        assertEquals(QUANTITY_35, storage.getQuantity(FRUIT_TANGERINE));
        assertEquals(QUANTITY_25, storage.getQuantity(FRUIT_GRAPES));
    }

    @Test
    void apply_zeroQuantity_ok() {
        StorageImpl storage = new StorageImpl();
        storage.setQuantity(FRUIT_MANGO, QUANTITY_10);

        operation.apply(FRUIT_MANGO, QUANTITY_0, storage);
        assertEquals(QUANTITY_10, storage.getQuantity(FRUIT_MANGO));
    }
}
