package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void testBalanceOperation() {
        OperationHandler handler = new BalanceOperation();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana",
                20
        );

        handler.handle(transaction);
        assertEquals(20, Storage.storage.get("banana").intValue());
    }
}
