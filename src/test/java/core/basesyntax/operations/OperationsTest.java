package core.basesyntax.operations;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OperationsTest {
    private static final String BALANCE = "B";
    private static final String PURCHASE = "P";
    private static final String RETURN = "R";
    private static final String SUPPLY = "S";
    private static final String WRONG = "A";

    @Test
    public void checkCorrectInput_Ok() {
        assertTrue(Operations.contains(BALANCE));
        assertTrue(Operations.contains(PURCHASE));
        assertTrue(Operations.contains(RETURN));
        assertTrue(Operations.contains(SUPPLY));
    }

    @Test
    public void checkIncorrectInput_NotOk() {
        assertFalse(Operations.contains(WRONG));
    }
}
