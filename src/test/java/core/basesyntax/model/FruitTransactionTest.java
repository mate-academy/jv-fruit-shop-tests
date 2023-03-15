package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class FruitTransactionTest {
    private FruitTransaction.Operation operation;
    private Exception exception;
    private String invalidCode;

    @Test
    public void getByCode_invalidValue_notOk() {
        invalidCode = "q";
        try {
            operation = FruitTransaction.Operation.getByCode(invalidCode);
        } catch (RuntimeException e) {
            exception = e;
        }
        assertNotNull(exception);
        assertEquals("Can`t get operation! Invalid code " + invalidCode,
                exception.getMessage());
    }
}
