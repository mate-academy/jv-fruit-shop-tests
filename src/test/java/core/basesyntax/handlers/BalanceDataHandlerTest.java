package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.FruitsNameException;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.handlers.BalanceDataHandler;
import core.basesyntax.strategy.handlers.DataHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceDataHandlerTest {
    private static DataHandler dataHandler;
    private static final String APPLE = "apple";
    private static final int APPLE_QUANTITY = 0;
    private static final String NULL_FRUIT = null;
    private static final String EMPTY_FRUIT = "";
    private static final int NEGATIVE_APPLE_QUANTITY = -59;

    @BeforeAll
    static void createStorage() {
        Storage.createMap();
    }

    @BeforeEach
    void setUp() {
        dataHandler = new BalanceDataHandler();
    }

    @Test
    void processData_validData_okay() {
        assertDoesNotThrow(() -> dataHandler.processData(APPLE, APPLE_QUANTITY));
        assertEquals(APPLE_QUANTITY, Storage.getFruits(APPLE));
    }

    @Test
    void processData_negativeQuantity_notOkay() {
        assertThrows(RuntimeException.class,
                () -> dataHandler.processData(APPLE, NEGATIVE_APPLE_QUANTITY));
    }

    @Test
    void process_nullFruit_notOkay() {
        assertThrows(FruitsNameException.class,
                () -> dataHandler.processData(NULL_FRUIT, APPLE_QUANTITY));
    }

    @Test
    void process_emptyFruit_notOkay() {
        assertThrows(FruitsNameException.class,
                () -> dataHandler.processData(EMPTY_FRUIT, APPLE_QUANTITY));
    }

    @AfterEach
    void cleanStorage() {
        Storage.clear();
    }
}
