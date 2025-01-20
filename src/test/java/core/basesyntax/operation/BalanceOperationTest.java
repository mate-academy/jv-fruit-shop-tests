package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static final FruitTransaction INVALID_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", -20);
    private static final String TEST_FRUIT = "banana";
    private static final FruitTransaction TRANSACTION_EXAMPLE =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 20);
    private static final int EXPECTED_QUANTITY = 20;
    private OperationHandler balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
    }

    @Test
    void handle_validTransaction_setsBalance() {
        balanceOperation.handle(TRANSACTION_EXAMPLE);
        assertEquals(EXPECTED_QUANTITY, Storage.getQuantity(TEST_FRUIT));
    }

    @Test
    void handle_negativeQuantity_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.handle(INVALID_TRANSACTION));
    }
}
