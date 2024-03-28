package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 20;
    private final Storage storage = new Storage();
    private BalanceHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BalanceHandler(storage);
    }

    @Test
    void put_informationToStorage_Ok() {
        assertNull(storage.getData().get(FRUIT),
                "Fruit should not exist in storage before operation.");
        handler.operate(FRUIT, QUANTITY);
        assertEquals(QUANTITY, storage.getData().get(FRUIT),
                "Storage does not contain the correct quantity of the fruit after operation.");
    }
}
