package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.handlers.FruitTransactionException;
import org.junit.Test;

public class FruitTransactionTest {
    public static final FruitTransaction.Operation EXPECTED_OPERATION
            = FruitTransaction.Operation.BALANCE;
    public static final String EXPECTED_NAME = "banana";
    public static final int EXPECTED_QUANTITY = 40;

    @Test
    public void getterReturnValue_ok() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", EXPECTED_QUANTITY);
        assertEquals(EXPECTED_OPERATION + " expected, but was " + transaction.getOperation(),
                EXPECTED_OPERATION, transaction.getOperation());
        assertEquals(EXPECTED_NAME + " expected, but was " + transaction.getFruit(),
                EXPECTED_NAME, transaction.getFruit());
        assertEquals(EXPECTED_QUANTITY + " expected, but was " + transaction.getQuantity(),
                EXPECTED_QUANTITY, transaction.getQuantity());
    }

    @Test(expected = FruitTransactionException.class)
    public void negativeNumberQuantity_notOk() {
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", -EXPECTED_QUANTITY);
    }
}
