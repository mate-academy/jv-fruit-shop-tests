package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {

    @BeforeAll
    static void beforeAll() {
        Storage.storage.clear();
        Storage.storage.put("banana", 200);
    }

    @Test
    void handler_ReturnCorrectWork_Ok() {
        ReturnHandler returnHandler = new ReturnHandler();
        returnHandler.execute("banana", 50);
        assertEquals(Storage.storage.get("banana"), 250);
    }
}
