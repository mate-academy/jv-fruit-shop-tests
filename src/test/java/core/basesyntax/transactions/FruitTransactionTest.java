package core.basesyntax.transactions;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String WRONG_CODE = "u";
    private static final String RIGHT_CODE = "s";

    @Test
    void transfer_WrongCodeOfOperation_NotOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.coverToOperation(WRONG_CODE);
        });
    }

    @Test
    void transfer_RightCodeOfOperation_Ok() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.coverToOperation(RIGHT_CODE);
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        Assert.assertEquals(actual,expected);
    }
}