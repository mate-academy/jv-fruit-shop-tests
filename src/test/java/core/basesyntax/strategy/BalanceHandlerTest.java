package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handle_newFruit_ok() {
        new BalanceHandler(storage).handle("banana", 100);
        assertEquals(100, (int) storage.get("banana"));
    }

    @Test
    void handle_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () ->
                new BalanceHandler(storage).handle("banana", -10));
    }
}
