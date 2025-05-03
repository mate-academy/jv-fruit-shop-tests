package core.basesyntax.transactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String WRONG_CODE = "u";
    private static final String RIGHT_CODE = "s";

    @Test
    void transfer_WrongCodeOfOperation_NotOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.coverToOperation(WRONG_CODE);
        });
    }

    @Test
    void transfer_RightCodeOfOperation_Ok() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.coverToOperation(RIGHT_CODE);
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        assertEquals(actual,expected);
    }
}
