package core.basesyntax.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OperationTest {
    private static final String BALANCE_CODE = "b";
    private static final String SUPPLY_CODE = "s";
    private static final String PURCHASE_CODE = "p";
    private static final String RETURN_CODE = "r";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private final Operation[] operations = new Operation[]
            {Operation.BALANCE, Operation.SUPPLY, Operation.PURCHASE, Operation.RETURN};

    @Test
    public void getOperationCode_Ok() {
        final String actualCodeB = Operation.BALANCE.getOperationCode();
        final String actualCodeP = Operation.PURCHASE.getOperationCode();
        final String actualCodeR = Operation.RETURN.getOperationCode();
        final String actualCodeS = Operation.SUPPLY.getOperationCode();
        assertEquals(BALANCE_CODE, actualCodeB);
        assertEquals(PURCHASE_CODE, actualCodeP);
        assertEquals(RETURN_CODE, actualCodeR);
        assertEquals(SUPPLY_CODE, actualCodeS);
    }

    @Test
    public void getByCode_Ok() {
        final Operation actualByCodeB = Operation.getByCode(BALANCE_CODE);
        final Operation actualByCodeP = Operation.getByCode(PURCHASE_CODE);
        final Operation actualByCodeR = Operation.getByCode(RETURN_CODE);
        final Operation actualByCodeS = Operation.getByCode(SUPPLY_CODE);
        assertEquals(Operation.BALANCE, actualByCodeB);
        assertEquals(Operation.PURCHASE, actualByCodeP);
        assertEquals(Operation.RETURN, actualByCodeR);
        assertEquals(Operation.SUPPLY, actualByCodeS);
    }

    @Test
    public void values_Ok() {
        Operation[] actualValues = Operation.values();
        assertArrayEquals(operations, actualValues);
    }

    @Test
    public void getNotExistOperationCode_NotOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(("Unsupported operation code: "));
        Operation.getByCode("123");
    }
}
