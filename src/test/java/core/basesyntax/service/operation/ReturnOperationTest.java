package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private Storage storage;

    @BeforeEach
    void init() {
        storage = new Storage();
    }

    @Test
    void handle_shouldIncreaseQuantityOnReturn() {
        storage.put("orange", 1);
        ReturnOperation op = new ReturnOperation(storage);
        op.handle(new FruitTransaction(Operation.RETURN, "orange", 4));
        assertEquals(5, storage.get("orange"));
    }
}
