package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private Storage storage;
    private BalanceHandler handler;
    private final String fruit = "apple";
    private final int quantity = 20;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        handler = new BalanceHandler(storage);
    }

    @Test
    void put_informationToStorage_Ok() {
        assertNull(storage.getData().get(fruit),
                "Fruit should not exist in storage before operation.");
        handler.operate(fruit, quantity);
        assertEquals(quantity, storage.getData().get(fruit),
                "Storage does not contain the correct quantity of the fruit after operation.");
    }
}
