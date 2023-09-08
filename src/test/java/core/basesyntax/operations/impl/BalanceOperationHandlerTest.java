package core.basesyntax.operations.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.operations.OperationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private OperationHandler handler;
    private Storage storage;

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        handler = new BalanceOperationHandler();
        storage = new Storage();
    }

    @Test
    void execute() {
        Storage.addFruits("apple", 3);
        FruitTransaction fruitTransaction = new FruitTransaction("apple", 0, Operation.BALANCE);

        Assertions.assertEquals(3, handler.execute(fruitTransaction));
    }
}
