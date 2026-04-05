package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.ReturnHandler;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    @Test
    void handle_validReturn_ok() {
        Storage storage = new Storage();
        storage.add("banana", 10);
        new ReturnHandler(storage).handle("banana", 5);
        assertEquals(15, (int) storage.get("banana"));
    }
}
