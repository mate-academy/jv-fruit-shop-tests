package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private final BalanceOperation balanceOperation = new BalanceOperation();

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void process_validTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(100);
        transaction.setOperation(FruitTransaction.Operation.BALANCE);

        balanceOperation.process(transaction);

        assertEquals(100, Storage.fruits.get("banana"));
    }

    @Test
    void process_overwritesExistingBalance_ok() {
        Storage.fruits.put("banana", 50);
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(100);

        balanceOperation.process(transaction);

        assertEquals(100, Storage.fruits.get("banana"));
    }
}
