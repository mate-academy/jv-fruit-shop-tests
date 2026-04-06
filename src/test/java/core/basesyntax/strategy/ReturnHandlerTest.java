package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private Storage storage;
    private ReturnHandler returnHandler;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        returnHandler = new ReturnHandler(storage);
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handle_validReturn_ok() {
        storage.add("banana", 10);
        returnHandler.handle("banana", 5);
        assertEquals(15, (int) storage.get("banana"));
    }

    @Test
    void handle_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () ->
                returnHandler.handle("apple", -5));
    }
}
