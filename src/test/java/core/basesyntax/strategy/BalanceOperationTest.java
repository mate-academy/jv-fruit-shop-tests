package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        Storage.clearAll();
        balanceOperation = new BalanceOperation();
    }

    @Test
    void process_validTransaction_setQuantity() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "banana", 50);

        balanceOperation.process(fruitTransaction);
        assertEquals(50, Storage.getFruit("banana"));
    }
}
