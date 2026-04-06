package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private Storage storage;
    private SupplyHandler supplyHandler;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        supplyHandler = new SupplyHandler(storage);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handle_incrementExisting_ok() {
        storage.add("apple", 10);
        supplyHandler.handle("apple", 5);
        assertEquals(15, (int) storage.get("apple"));
    }

    @Test
    void handle_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () ->
                supplyHandler.handle("apple", -5));
    }
}
