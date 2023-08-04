package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitsNameException;
import core.basesyntax.exceptions.FruitsQuantityException;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.handlers.DataHandler;
import core.basesyntax.strategy.handlers.PurchaseDataHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseDataHandlerTest {
    private static DataHandler dataHandler;
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY_BIGGER_THAN_STORAGE = 60;
    private static final int APPLE_QUANTITY_SMALLER_THAN_STORAGE = 10;
    private static final int STORAGE_QUANTITY = 50;
    private static final String NULL_FRUIT = null;
    private static final String EMPTY_FRUIT = "";
    private static final int NEGATIVE_APPLE_QUANTITY = -59;

    @BeforeEach
    void setUp() {
        dataHandler = new PurchaseDataHandler();
        Storage.createMap();
    }

    @Test
    void smallerThanStoragePurchaseOkay() {
        Storage.addFruits(APPLE, STORAGE_QUANTITY);
        assertDoesNotThrow(() -> dataHandler.processData(APPLE,
                APPLE_QUANTITY_SMALLER_THAN_STORAGE));
        assertEquals(STORAGE_QUANTITY - APPLE_QUANTITY_SMALLER_THAN_STORAGE,
                Storage.getFruits(APPLE));
    }

    @Test
    void biggerThanStoragePurchaseNotOkay() {
        Storage.addFruits(APPLE, STORAGE_QUANTITY);
        assertThrows(FruitsQuantityException.class,
                () -> dataHandler.processData(APPLE, APPLE_QUANTITY_BIGGER_THAN_STORAGE));
    }

    @Test
    void addNegativeQuantityNotOkay() {
        assertThrows(FruitsQuantityException.class,
                () -> dataHandler.processData(APPLE, NEGATIVE_APPLE_QUANTITY));
    }

    @Test
    void addNullFruitNotOkay() {
        assertThrows(FruitsNameException.class, () -> dataHandler.processData(
                NULL_FRUIT, APPLE_QUANTITY_SMALLER_THAN_STORAGE));
    }

    @Test
    void addEmptyFruitNotOkay() {
        assertThrows(FruitsNameException.class, () -> dataHandler.processData(
                EMPTY_FRUIT, APPLE_QUANTITY_SMALLER_THAN_STORAGE));
    }
}
