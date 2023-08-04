package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitsNameException;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.handlers.DataHandler;
import core.basesyntax.strategy.handlers.SupplyDataHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyDataHandlerTest {
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY = 0;
    private static final String NULL_FRUIT = null;
    private static final String EMPTY_FRUIT = "";
    private static final int NEGATIVE_APPLE_QUANTITY = -59;
    private static DataHandler dataHandler;

    @BeforeEach
    void setUp() {
        dataHandler = new SupplyDataHandler();
        Storage.createMap();
    }

    @Test
    void addNormalQuantityOkay() {
        assertDoesNotThrow(() -> dataHandler.processData(APPLE, APPLE_QUANTITY));
        assertEquals(APPLE_QUANTITY, Storage.getFruits(APPLE));
    }

    @Test
    void addNegativeQuantityNotOkay() {
        assertThrows(RuntimeException.class,
                () -> dataHandler.processData(APPLE, NEGATIVE_APPLE_QUANTITY));
    }

    @Test
    void addNullFruitNotOkay() {
        assertThrows(FruitsNameException.class,
                () -> dataHandler.processData(NULL_FRUIT, APPLE_QUANTITY));
    }

    @Test
    void addEmptyFruitNotOkay() {
        assertThrows(FruitsNameException.class,
                () -> dataHandler.processData(EMPTY_FRUIT, APPLE_QUANTITY));
    }
}
