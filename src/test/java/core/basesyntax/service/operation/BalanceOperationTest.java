package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private Storage storage;

    @BeforeEach
    void init() {
        storage = new Storage();
    }

    @Test
    void handle_shouldSetValueOnBalance() {
        BalanceOperation op = new BalanceOperation(storage);
        op.handle(new FruitTransaction(Operation.BALANCE, "banana", 20));
        assertEquals(20, storage.get("banana"));
    }
}
