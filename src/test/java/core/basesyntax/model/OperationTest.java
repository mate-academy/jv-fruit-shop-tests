package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

public class OperationTest {

    private static final String INVALID_OPERATION = "a";
    private static final String VALID_OPERATION_BALANCE = "b";
    private static final String VALID_OPERATION_PURCHASE = "p";
    private static final String VALID_OPERATION_SUPPLY = "s";
    private static final String VALID_OPERATION_RETURN = "r";

    @Test
    public void getByCode_validValue_ok() {
        assertEquals(Operation.BALANCE,Operation.getByCode(VALID_OPERATION_BALANCE));
        assertEquals(Operation.PURCHASE,Operation.getByCode(VALID_OPERATION_PURCHASE));
        assertEquals(Operation.SUPPLY,Operation.getByCode(VALID_OPERATION_SUPPLY));
        assertEquals(Operation.RETURN,Operation.getByCode(VALID_OPERATION_RETURN));
    }

    @Test
    public void getByCode_invalidValue_notOk() {
        assertThrows(RuntimeException.class, () ->
                        Operation.getByCode(INVALID_OPERATION),
                "Can't find Operation by letter "
                        + INVALID_OPERATION);
    }

    @Test
    public void getOperation_validOperation_ok() {
        assertEquals(VALID_OPERATION_BALANCE, Operation.BALANCE.getOperation());
        assertEquals(VALID_OPERATION_PURCHASE, Operation.PURCHASE.getOperation());
        assertEquals(VALID_OPERATION_SUPPLY, Operation.SUPPLY.getOperation());
        assertEquals(VALID_OPERATION_RETURN, Operation.RETURN.getOperation());
    }
}
