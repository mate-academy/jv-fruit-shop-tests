package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private Storage storage;
    private OperationHandler balanceOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage(new HashMap<>());
        balanceOperation = new BalanceOperation(storage);
    }

    @Test
    void handle_addFruit_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("kiwi");
        transaction.setQuantity(100);
        balanceOperation.handle(transaction);
        assertEquals(100, storage.getFruitQuantity("kiwi"));
        assertEquals(0, storage.getFruitQuantity("banana"));
    }
}
