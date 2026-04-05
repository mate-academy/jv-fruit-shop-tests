package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.BalanceHandler;
import org.junit.jupiter.api.Test;

public class BalanceHandlerTest {
    @Test
    void handle_newFruit_ok() {
        Storage storage = new Storage();
        new BalanceHandler(storage).handle("banana", 100);
        assertEquals(100, (int) storage.get("banana"));
    }
}
