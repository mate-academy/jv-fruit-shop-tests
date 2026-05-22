package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void balanceOperation_shouldOverwriteExistingQuantity() {
        storage.getFruits().put("banana", 10);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.BALANCE);
        transaction.setFruit("banana");
        transaction.setQuantity(100);
        BalanceOperationHandler handler = new BalanceOperationHandler(storage);
        handler.apply(transaction);
        assertEquals(100, storage.getFruits().get("banana"));
    }
}
