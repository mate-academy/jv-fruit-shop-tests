package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private Storage storage;

    @BeforeEach
    void init() {
        storage = new Storage();
    }

    @Test
    void handle_shouldIncreaseQuantityOnSupply() {
        storage.put("orange", 5);
        SupplyOperation op = new SupplyOperation(storage);
        op.handle(new FruitTransaction(Operation.SUPPLY, "orange", 10));
        assertEquals(15, storage.get("orange"));
    }
}
