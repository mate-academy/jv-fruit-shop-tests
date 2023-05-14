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
    private final Activity.Operation[] operations = new Activity.Operation[]{
            Activity.Operation.BALANCE,
            Activity.Operation.SUPPLY,
            Activity.Operation.PURCHASE,
            Activity.Operation.RETURN
            };

    @Test
    public void getOperationCode_Ok() {
        final String actualCodeB = Activity.Operation.BALANCE.getOperationCode();
        final String actualCodeP = Activity.Operation.PURCHASE.getOperationCode();
        final String actualCodeR = Activity.Operation.RETURN.getOperationCode();
        final String actualCodeS = Activity.Operation.SUPPLY.getOperationCode();
        assertEquals(BALANCE_CODE, actualCodeB);
        assertEquals(PURCHASE_CODE, actualCodeP);
        assertEquals(RETURN_CODE, actualCodeR);
        assertEquals(SUPPLY_CODE, actualCodeS);
    }

    @Test
    public void getByCode_Ok() {
        final Activity.Operation actualByCodeB = Activity.Operation.getByCode(BALANCE_CODE);
        final Activity.Operation actualByCodeP = Activity.Operation.getByCode(PURCHASE_CODE);
        final Activity.Operation actualByCodeR = Activity.Operation.getByCode(RETURN_CODE);
        final Activity.Operation actualByCodeS = Activity.Operation.getByCode(SUPPLY_CODE);
        assertEquals(Activity.Operation.BALANCE, actualByCodeB);
        assertEquals(Activity.Operation.PURCHASE, actualByCodeP);
        assertEquals(Activity.Operation.RETURN, actualByCodeR);
        assertEquals(Activity.Operation.SUPPLY, actualByCodeS);
    }

    @Test
    public void values_Ok() {
        Activity.Operation[] actualValues = Activity.Operation.values();
        assertArrayEquals(operations, actualValues);
    }

    @Test
    public void getNotExistOperationCode_NotOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage(("Unsupported operation code: "));
        Activity.Operation.getByCode("123");
    }
}
