package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class OperationTest {

    private String invalidOperation = "a";
    private String validOperationB = "b";
    private String validOperationP = "p";
    private String validOperationS = "s";
    private String validOperationR = "r";

    @Test
    public void getByCode_validValue_ok() {
        assertEquals(Operation.BALANCE,Operation.getByCode(validOperationB));
        assertEquals(Operation.PURCHASE,Operation.getByCode(validOperationP));
        assertEquals(Operation.SUPPLY,Operation.getByCode(validOperationS));
        assertEquals(Operation.RETURN,Operation.getByCode(validOperationR));
    }

    @Test
    public void getByCode_invalidValue_notOk() {
        assertThrows(RuntimeException.class, () ->
                        Operation.getByCode(invalidOperation),
                "Can't find Operation by letter "
                        + invalidOperation);
    }

    @Test
    public void getOperation_validOperation_ok() {
        assertEquals(validOperationB, Operation.BALANCE.getOperation());
        assertEquals(validOperationP, Operation.PURCHASE.getOperation());
        assertEquals(validOperationS, Operation.SUPPLY.getOperation());
        assertEquals(validOperationR, Operation.RETURN.getOperation());
    }
}
