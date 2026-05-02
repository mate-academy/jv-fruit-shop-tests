package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.basesyntax.db.StorageImpl;
import core.basesyntax.basesyntax.strategy.OperationHandler;
import core.basesyntax.basesyntax.strategy.PurchaseOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_ORANGE = "orange";
    private static final String FRUIT_GRAPES = "grapes";
    private static final String NOT_FRUIT_TOMATO = "tomato";
    private static final int QUANTITY_0 = 0;
    private static final int QUANTITY_4 = 4;
    private static final int QUANTITY_5 = 5;
    private static final int QUANTITY_6 = 6;
    private static final int QUANTITY_10 = 10;
    private static final int QUANTITY_15 = 15;
    private static final int QUANTITY_20 = 20;
    private static final int QUANTITY_30 = 30;

    private OperationHandler operation;

    @BeforeEach
    void setUp() {
        operation = new PurchaseOperation();
    }

    @Test
    void apply_validPurchase_ok() {
        StorageImpl storage = new StorageImpl();
        storage.setQuantity(FRUIT_APPLE, QUANTITY_10);
        storage.setQuantity(FRUIT_BANANA, QUANTITY_20);
        storage.setQuantity(FRUIT_ORANGE, QUANTITY_15);

        operation.apply(FRUIT_APPLE, QUANTITY_6, storage);
        operation.apply(FRUIT_ORANGE, QUANTITY_5, storage);

        assertEquals(QUANTITY_4, storage.getQuantity(FRUIT_APPLE));
        assertEquals(QUANTITY_20, storage.getQuantity(FRUIT_BANANA));
        assertEquals(QUANTITY_10, storage.getQuantity(FRUIT_ORANGE));
    }

    @Test
    void apply_exactStock_ok() {
        StorageImpl storage = new StorageImpl();
        storage.setQuantity(FRUIT_BANANA, QUANTITY_5);

        operation.apply(FRUIT_BANANA, QUANTITY_5, storage);
        assertEquals(QUANTITY_0, storage.getQuantity(FRUIT_BANANA));
    }

    @Test
    void apply_notEnoughStock_notOK() {
        StorageImpl storage = new StorageImpl();
        storage.setQuantity(FRUIT_GRAPES, QUANTITY_4);

        assertThrows(RuntimeException.class,
                () -> operation.apply(FRUIT_GRAPES, QUANTITY_5, storage));
    }

    @Test
    void apply_notFruit_notOk() {
        StorageImpl storage = new StorageImpl();
        storage.setQuantity(FRUIT_APPLE, QUANTITY_20);
        storage.setQuantity(FRUIT_BANANA, QUANTITY_30);

        assertThrows(RuntimeException.class,
                () -> operation.apply(NOT_FRUIT_TOMATO, QUANTITY_5, storage));
    }
}
