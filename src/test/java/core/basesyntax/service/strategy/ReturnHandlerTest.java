package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private Storage storage;
    private BalanceHandler handler;
    private ReturnHandler returnHandler;
    private final String fruit = "apple";
    private final int quantity = 20;
    private final int addQuantity = 5;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        handler = new BalanceHandler(storage);
        returnHandler = new ReturnHandler(storage);
    }

    @Test
    void put_newInformationToStorage_Ok() {
        assertNull(storage.getData().get(fruit),
                "Fruit should not exist in storage before operation.");
        handler.operate(fruit, quantity);
        int actualQuantity = quantity + addQuantity;
        returnHandler.operate(fruit, addQuantity);

        assertEquals(actualQuantity, storage.getData().get(fruit),
                "Storage does not contain the correct quantity of the fruit after operation.");
    }
}
