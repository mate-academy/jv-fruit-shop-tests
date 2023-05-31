package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class FruitTransactionTest {
    private static final String TEST_OPERATION = "s";
    private static final String INVALID_TEST_OPERATION = "z";

    @Test
    public void getOperationByLetter_Ok() {
        FruitTransaction.Operation actual =
                FruitTransaction.Operation.BALANCE.getOperationByLetter(TEST_OPERATION);
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        assertEquals(expected,actual);
    }

    @Test
    public void getOperationByLetter_InvalidData_NotOk() {
        assertThrows(RuntimeException.class, () ->
                FruitTransaction.Operation.BALANCE.getOperationByLetter(INVALID_TEST_OPERATION));
    }
}
