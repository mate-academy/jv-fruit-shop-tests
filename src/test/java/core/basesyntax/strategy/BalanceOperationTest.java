package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private OperationHandler balanceOperation;

    @BeforeEach
    void setUp() {
        Storage.clear();
        balanceOperation = new BalanceOperation();
    }

    @Test
    void process_validTransaction_Ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        100);
        balanceOperation.process(transaction);
        assertEquals(100, Storage.get("banana"));
    }
}
