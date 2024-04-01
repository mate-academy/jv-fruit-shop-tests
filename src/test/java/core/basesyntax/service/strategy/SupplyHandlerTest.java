package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static final String FRUIT = "apple";
    private static final int QUANTITY = 20;
    private static final int ADD_QUANTITY = 200;
    private Storage storage;
    private SupplyHandler supplyHandler;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        supplyHandler = new SupplyHandler(storage);
        storage.getData().clear();
    }

    @Test
    void put_changedInformationToStorage_Ok() {
        storage.getData().put(FRUIT, QUANTITY);
        int actualQuantity = QUANTITY + ADD_QUANTITY;
        supplyHandler.operate(FRUIT, ADD_QUANTITY);

        assertEquals(actualQuantity, storage.getData().get(FRUIT),
                "Storage does not contain the correct quantity of the fruit after operation.");
    }
}
