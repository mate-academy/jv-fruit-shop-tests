package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 20;
    private static final int ADD_QUANTITY = 5;
    private Storage storage;
    private BalanceHandler handler;
    private ReturnHandler returnHandler;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        handler = new BalanceHandler(storage);
        returnHandler = new ReturnHandler(storage);
    }

    @Test
    void put_changedInformationToStorage_Ok() {
        assertNull(storage.getData().get(FRUIT),
                "Fruit should not exist in storage before operation.");
        handler.operate(FRUIT, QUANTITY);
        int actualQuantity = QUANTITY + ADD_QUANTITY;
        returnHandler.operate(FRUIT, ADD_QUANTITY);

        assertEquals(actualQuantity, storage.getData().get(FRUIT),
                "Storage does not contain the correct quantity of the fruit after operation.");
    }
}
