package core.basesyntax.operations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OperationsTest {
    private static final String PURCHASE_TYPE = "P";
    private static final String BALANCE_TYPE = "B";
    private static final String SUPPLY_TYPE = "S";
    private static final String RETURN_TYPE = "R";
    private static final String WRONG_TYPE = "Z";

    @Test
    public void containsTestWithCorrectInput_Ok() {
        assertTrue(Operations.contains(PURCHASE_TYPE));
        assertTrue(Operations.contains(BALANCE_TYPE));
        assertTrue(Operations.contains(SUPPLY_TYPE));
        assertTrue(Operations.contains(RETURN_TYPE));
    }

    @Test
    public void containsTestWithWrongInput_NotOk() {
        assertFalse(Operations.contains(WRONG_TYPE));
    }
}
