package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationImplTest {
    private static final int NORMAL_QUANTITY = 1;
    private static final int NEGATIVE_QUANTITY = -1;
    private static final String BANANA = "banana";
    private final OperationHandler balanceOperation = new BalanceOperationImpl();

    @BeforeEach
    void setUp() {
        Storage.clearDb();
    }

    @Test
    void applyOperation_correctBalance_ok() {
        FruitTransaction transaction = new FruitTransaction(
                Operation.BALANCE,
                BANANA,
                NORMAL_QUANTITY);
        balanceOperation.applyOperation(transaction);
        assertEquals(NORMAL_QUANTITY, Storage.getQuantity(BANANA));
    }

    @Test
    void applyOperation_negativeBalance_notOk() {
        FruitTransaction transaction = new FruitTransaction(
                Operation.BALANCE,
                BANANA,
                NEGATIVE_QUANTITY);
        assertThrows(RuntimeException.class, () ->
                balanceOperation.applyOperation(transaction));
    }
}
