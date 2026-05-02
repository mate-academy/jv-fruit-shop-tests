package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.basesyntax.db.StorageImpl;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import core.basesyntax.basesyntax.strategy.ReturnOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_ORANGE = "orange";
    private static final String FRUIT_GRAPES = "grapes";
    private static final int QUANTITY_5 = 5;
    private static final int QUANTITY_15 = 15;
    private static final int QUANTITY_20 = 20;
    private static final int QUANTITY_25 = 25;

    private OperationHandler operation;

    @BeforeEach
    void setUp() {
        operation = new ReturnOperation();
    }

    @Test
    void apply_returnFruit_ok() {
        StorageImpl storage = new StorageImpl();
        storage.setQuantity(FRUIT_ORANGE, QUANTITY_15);
        storage.setQuantity(FRUIT_BANANA, QUANTITY_25);
        storage.setQuantity(FRUIT_APPLE, QUANTITY_20);
        storage.setQuantity(FRUIT_GRAPES, QUANTITY_15);

        operation.apply(FRUIT_ORANGE, QUANTITY_5, storage);
        operation.apply(FRUIT_GRAPES, QUANTITY_5, storage);

        assertEquals(QUANTITY_20, storage.getQuantity("orange"));
        assertEquals(QUANTITY_25, storage.getQuantity("banana"));
        assertEquals(QUANTITY_20, storage.getQuantity("apple"));
        assertEquals(QUANTITY_20, storage.getQuantity("grapes"));
    }
}
